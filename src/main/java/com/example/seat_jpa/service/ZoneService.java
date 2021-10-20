package com.example.seat_jpa.service;

import com.example.seat_jpa.entity.po.User;
import com.example.seat_jpa.entity.po.Zone;
import com.example.seat_jpa.param.ZoneQueryParam;
import org.springframework.data.domain.Page;

/**
 * @author 橙鼠鼠
 */
public interface ZoneService {
    Integer addZone(String description, String name, Integer userID);
    Page<Zone> findZoneByFilter(ZoneQueryParam param);
}
