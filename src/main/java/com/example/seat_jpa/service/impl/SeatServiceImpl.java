package com.example.seat_jpa.service.impl;

import com.example.seat_jpa.dao.SeatRepository;
import com.example.seat_jpa.dao.ZoneRepository;
import com.example.seat_jpa.entity.po.Seat;
import com.example.seat_jpa.project.exception.ErrorInfoEnum;
import com.example.seat_jpa.project.exception.ProjectException;
import com.example.seat_jpa.service.SeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 橙鼠鼠
 */
@Service
@Slf4j
public class SeatServiceImpl implements SeatService {
    private ZoneRepository zoneRepository;
    private SeatRepository seatRepository;


    @Autowired
    public SeatServiceImpl setZoneRepository(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
        return this;
    }

    @Autowired
    public SeatServiceImpl setSeatRepository(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
        return this;
    }

    @Override
    public Integer addSeat(String description, Integer zoneId) {
        var zone = zoneRepository.findById(zoneId);
        if(zone.isEmpty()){
            throw new ProjectException("插入椅子时,对应场所不存在",ErrorInfoEnum.CRUD_ERROR);
        }
        var seat = new Seat.Builder().setDescription(description).setZone(zone.get()).build();
        var seat1 = seatRepository.save(seat);
        log.info("save seat{}",seat1);
        return seat1.getId();
    }
}
