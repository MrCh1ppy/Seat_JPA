package com.example.seat_jpa.dao;

import com.example.seat_jpa.entity.po.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 橙鼠鼠
 */
public interface SeatRepository extends JpaRepository<Seat, Integer> {
}