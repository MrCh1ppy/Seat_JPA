package com.example.seat_jpa.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 橙鼠鼠
 */
@Data
@AllArgsConstructor
@ApiModel("座位添加参数")
public class SeatAddParam {
    @ApiParam("描述")
    private String description;
    @ApiParam("场所ID")
    @NotNull
    private Integer zoneId;
}
