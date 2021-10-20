package com.example.seat_jpa.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 橙鼠鼠
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("区域查询实体")
public class ZoneQueryParam extends BasePageAbleParam{
    @ApiParam("名称")
    String name;
    @ApiParam("管理员名称")
    String adminName;
}
