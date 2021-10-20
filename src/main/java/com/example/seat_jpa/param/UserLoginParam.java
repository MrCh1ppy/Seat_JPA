package com.example.seat_jpa.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 橙鼠鼠
 */
@ApiModel("用户登录参数")
@Data
@AllArgsConstructor
public class UserLoginParam{
       @ApiParam(required = true) String username;
       @ApiParam(required = true) String password;
       @ApiParam(required = true) Boolean isAdmin;
}
