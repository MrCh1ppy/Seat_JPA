package com.example.seat_jpa.service.impl;

import com.example.seat_jpa.dao.SeatRepository;
import com.example.seat_jpa.dao.UserRepository;
import com.example.seat_jpa.dao.ZoneRepository;
import com.example.seat_jpa.entity.dto.AppealResponse;
import com.example.seat_jpa.entity.dto.SeatRightResponse;
import com.example.seat_jpa.entity.po.Seat;
import com.example.seat_jpa.project.exception.ErrorInfoEnum;
import com.example.seat_jpa.project.exception.ProjectException;
import com.example.seat_jpa.service.SeatOccupancyService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author 橙鼠鼠
 */
@Service
@Slf4j
public class SeatOccupancyServiceImpl implements SeatOccupancyService {
    private SeatRepository seatRepository;
    private ZoneRepository zoneRepository;
    private UserRepository userRepository;
    private RedisTemplate<String,String> redisTemplate;
    private static final String CONFIRM_QUEUE="c_queue";
    private static final String SECURITY_QUEUE="s_queue";
    public static final String CALL_SET="c_set";
    private static final String SAFE_TIME="s_time";
    public static final String CONFIRM_TIME="c_time";
    private static final String DEFAULT_SAFE_TIME ="1200000";
    public static final String DEFAULT_CONFIRM_TIME="300000";

    @Autowired
    public SeatOccupancyServiceImpl setSeatRepository(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
        return this;
    }

    @Autowired
    public SeatOccupancyServiceImpl setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        return this;
    }

    @Autowired
    public SeatOccupancyServiceImpl setZoneRepository(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
        return this;
    }

    @Autowired
    public SeatOccupancyServiceImpl setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        return this;
    }

    @Override
    public Boolean occupy(Integer seatId) throws ProjectException {
        var seat = ifSeatExist(seatId);
        //从待确认队列删除这个被申诉的椅子(如果已经被申诉的话)
        redisTemplate.opsForZSet()
                .remove(CONFIRM_QUEUE,seat.getId());
        //在安全队列中添加这个座位,这个座位进入安全区
        return redisTemplate.opsForZSet()
                .add(SECURITY_QUEUE, String.valueOf(seat.getId()), calScore());
    }

    @Contract(pure = true)
    private double calScore(){
        while (redisTemplate.opsForValue().get(SAFE_TIME)==null){
            redisTemplate.opsForValue().setIfPresent(SAFE_TIME, DEFAULT_SAFE_TIME);
        }
        var safeTimeString = redisTemplate.opsForValue().get(SAFE_TIME);
        assert safeTimeString != null;
        var safeTimeInLong = Long.parseLong(safeTimeString);
        return System.currentTimeMillis() + (double)safeTimeInLong;
    }

    private @NotNull Seat ifSeatExist(Integer seatId)throws ProjectException{
        var byId = seatRepository.findById(seatId);
        if(byId.isEmpty()){
            throw new ProjectException("该椅子不存在", ErrorInfoEnum.PROJECT_ERROR);
        }
        return byId.get();
    }

    private boolean isInSecurityQueue(@NotNull Integer seatId){
        return redisTemplate.opsForZSet().score(SECURITY_QUEUE,seatId.toString())!=null;
    }

    @Override
    public AppealResponse appeal(Integer seatId) throws ProjectException {
        var seat = ifSeatExist(seatId);
        //判断其不在安全队列中
        if(isInSecurityQueue(seatId)){
            return AppealResponse.UNDER_PROTECT;
        }else {
            var response = checkSeatRight(seat);
            switch (response){
                case SPECIAL :{
                    return AppealResponse.SPECIAL_SEAT;
                }
                case KY_TYPE:{
                    return AppealResponse.KY_SEAT;
                }
                default:{
                    //或目前没在待确认队列则加入
                    //如果已经加入了则不变
                    //&&是按顺序的并列,所以ok
                    if (!inConfirmQueue(seat.getId())&&!putInConfirmQueue(seatId)) {
                        throw new ProjectException("未能成功添加至待确认队列",ErrorInfoEnum.PROJECT_ERROR);
                    }
                    return AppealResponse.SUCCESS;
                }
            }
        }
    }

    private SeatRightResponse checkSeatRight(@NotNull Seat seat){
        var i = seat.getId() % 3;
        SeatRightResponse response;
        response=switch (i){
            case 1->SeatRightResponse.SPECIAL;
            case 2->SeatRightResponse.KY_TYPE;
            default -> SeatRightResponse.NORMAL;
        };
        return response;
    }

    private boolean inConfirmQueue(@NotNull Integer seatId){
        return redisTemplate.opsForZSet()
                .score(CONFIRM_QUEUE,seatId.toString())!=null;
    }

    private boolean putInConfirmQueue(Integer seatId){
        String cTimeString=redisTemplate.opsForValue().get(CONFIRM_TIME);
        if(cTimeString==null){
            cTimeString=DEFAULT_CONFIRM_TIME;
            redisTemplate.opsForValue().setIfPresent(CONFIRM_TIME,DEFAULT_CONFIRM_TIME);
        }
        var score = System.currentTimeMillis() + Long.parseLong(cTimeString);
        return Boolean.TRUE.equals(redisTemplate.opsForZSet().add(CONFIRM_QUEUE, seatId.toString(), score));
    }

    @Override
    public List<Seat> getCallList() {
        return null;
    }

    @Override
    public List<Seat> getCallList(Integer zoneId) {
        return null;
    }

    @Scheduled(fixedRate = 1000)
    protected void redisFresh(){
        var cur = System.currentTimeMillis();
        //删除数值小于自身的数据
        var remove1 = redisTemplate.opsForZSet().removeRangeByScore(SECURITY_QUEUE, 0, cur);
        var idSet = redisTemplate.opsForZSet().rangeByScore(CONFIRM_QUEUE, 0, cur);
        idSet=idSet==null? Collections.emptySet():idSet;
        for (String id : idSet) {
            redisTemplate.opsForSet().add(CALL_SET,id);
        }
        var remove2 = redisTemplate.opsForZSet().removeRangeByScore(CONFIRM_QUEUE, 0, cur);
        remove1=remove1==null?0:remove1;
        remove2=remove2==null?0:remove2;
        if(remove1>0||remove2>0){
            var res = new StringBuilder("时间戳").append(cur)
                    .append("安全区删除").append(remove1)
                    .append("确认区删除").append(remove2)
                    .append("传唤区新增").append(idSet.size())
                    .toString();
            System.out.println(res);
            log.info(res);
        }

    }
}
