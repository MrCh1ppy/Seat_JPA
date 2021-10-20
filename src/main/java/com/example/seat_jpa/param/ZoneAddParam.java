package com.example.seat_jpa.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 橙鼠鼠
 */
@ApiModel("区域添加参数")
@Data
@AllArgsConstructor
public class ZoneAddParam{
        @ApiParam("描述")
       String description;
       @ApiParam(value = "名称",required = true)
       String name;
       @ApiParam(value = "管理员ID",required = true)
       Integer adminId;

}
