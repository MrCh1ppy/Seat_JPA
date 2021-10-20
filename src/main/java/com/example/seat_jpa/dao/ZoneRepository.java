package com.example.seat_jpa.dao;

import com.example.seat_jpa.entity.po.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 橙鼠鼠
 */
public interface ZoneRepository extends JpaRepository<Zone, Integer> , JpaSpecificationExecutor<Zone> {
}