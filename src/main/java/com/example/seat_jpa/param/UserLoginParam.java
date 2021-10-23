package com.example.seat_jpa.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 橙鼠鼠
 */
@ApiModel("用户登录参数")
@Data
@AllArgsConstructor
public class UserLoginParam{
       @ApiParam(required = true)
       @NotNull
       @Size(min =1)
       String username;
       @ApiParam(required = true)
       @Size(min = 1)
       @NotNull
       String password;
       @ApiParam(required = true)
       @NotNull
       Boolean isAdmin;
}
