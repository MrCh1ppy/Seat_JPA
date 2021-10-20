package com.example.seat_jpa.controller;

import com.example.seat_jpa.component.ApiResult;
import com.example.seat_jpa.entity.po.Zone;
import com.example.seat_jpa.param.ZoneAddParam;
import com.example.seat_jpa.param.ZoneQueryParam;
import com.example.seat_jpa.service.ZoneService;
import io.swagger.v3.oas.annotations.Operation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 橙鼠鼠
 */
@RestController
@RequestMapping("/zone")
public class ZoneController {
    private ZoneService zoneService;

    @Autowired
    public ZoneController setZoneService(ZoneService zoneService) {
        this.zoneService = zoneService;
        return this;
    }

    @PostMapping("/query")
    public ApiResult<Page<Zone>> getZoneInFilter(ZoneQueryParam param){
        var filter = zoneService.findZoneByFilter(param);
        return ApiResult.ok(filter);
    }

    @PostMapping("/add")
    @Operation(description = "添加场所")
    public ApiResult<Integer>addZone(@NotNull ZoneAddParam param){
        var id = zoneService.addZone(param.getDescription(), param.getName(), param.getAdminId());
        return ApiResult.ok(id);
    }
}
