package com.example.seat_jpa.service;

import com.example.seat_jpa.entity.enum_package.AppealResponse;
import com.example.seat_jpa.entity.po.Seat;
import com.example.seat_jpa.project.exception.ProjectException;

import java.util.List;

/**
 * @author 橙鼠鼠
 */
public interface SeatOccupancyService {
    Boolean occupy(Integer seatId)throws ProjectException;
    AppealResponse appeal(Integer seatId) throws ProjectException;
    List<Seat> getCallList();
    List<Seat> getCallList(Integer zoneId);

}
