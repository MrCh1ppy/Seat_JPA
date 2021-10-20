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
@ApiModel("用户查询参数")
public class UserQueryParam extends BasePageAbleParam{
    @ApiParam(value = "用户名",defaultValue = "root")
    private final String username;
    @ApiParam(value = "是否为管理员",defaultValue = "true")
    private final Boolean isAdmin;
}
