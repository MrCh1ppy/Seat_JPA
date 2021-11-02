package com.example.seat_jpa.controller;

import com.example.seat_jpa.component.ApiResult;
import com.example.seat_jpa.param.SeatAddParam;
import com.example.seat_jpa.service.SeatService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 橙鼠鼠
 */
@RestController
@RequestMapping("/seat")
public class SeatController {
    private SeatService seatService;

    @Autowired
    public SeatController setSeatService(SeatService seatService) {
        this.seatService = seatService;
        return this;
    }

    @PostMapping("/enroll")
    public ApiResult<Integer> addSeat(@Validated @NotNull SeatAddParam param){
        var seatId = seatService.addSeat(param.getDescription(), param.getZoneId());
        return ApiResult.ok(seatId);
    }
}
