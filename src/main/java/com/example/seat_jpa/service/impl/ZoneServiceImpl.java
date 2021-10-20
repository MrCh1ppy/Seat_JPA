package com.example.seat_jpa.service.impl;

import com.example.seat_jpa.dao.UserRepository;
import com.example.seat_jpa.dao.ZoneRepository;
import com.example.seat_jpa.entity.po.User;
import com.example.seat_jpa.entity.po.Zone;
import com.example.seat_jpa.param.ZoneQueryParam;
import com.example.seat_jpa.project.exception.ErrorInfoEnum;
import com.example.seat_jpa.project.exception.ProjectException;
import com.example.seat_jpa.service.ZoneService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 橙鼠鼠
 */
@Service
@Slf4j
public class ZoneServiceImpl implements ZoneService {
    private ZoneRepository zoneRepository;
    private UserRepository userRepository;

    @Autowired
    public ZoneServiceImpl setZoneRepository(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
        return this;
    }

    @Autowired
    public ZoneServiceImpl setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        return this;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer addZone(String description, String name, @NotNull User user) {
        if(Boolean.FALSE.equals(user.getIsAdmin())){
            throw new ProjectException("非管理员User不得成为场所管理员", ErrorInfoEnum.PROJECT_ERROR);
        }
        var zone = new Zone.Builder()
                .setAdmin(user)
                .setDescription(description)
                .setName(name)
                .build();
        var zone1 = zoneRepository.save(zone);
        log.info("save zone{}",zone1);
        return zone1.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addZone(String description, String name, Integer userId){
        var user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new ProjectException("用户名不存在",ErrorInfoEnum.PROJECT_ERROR);
        }
        return addZone(description, name, user.get());
    }

    @Override
    public Page<Zone> findZoneByFilter(@NotNull ZoneQueryParam param) {
        return zoneRepository.findAll((Specification<Zone>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (param.getName()!=null&&!param.getName().isBlank()) {
                predicates.add(criteriaBuilder
                        .like(root.get("name"), "%" + param.getName() + "%"));
            }
            if (param.getAdminName()!=null&&!param.getAdminName().isBlank()) {
                predicates.add(criteriaBuilder
                        .like(root
                        //获取Zone的admin属性
                        .get("admin")
                        //获取admin属性的name属性
                        //表的类型会自动推断
                        .get("userName"), "%" + param.getAdminName() + "%"));
            }
            for (Predicate predicate : predicates) {
                query.where(predicate);
            }
            return null;
        }, param.toPageRequest());
    }

}
