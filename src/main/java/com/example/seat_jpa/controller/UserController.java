package com.example.seat_jpa.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.seat_jpa.component.ApiResult;
import com.example.seat_jpa.entity.po.User;
import com.example.seat_jpa.param.UserEnrollParam;
import com.example.seat_jpa.param.UserLoginParam;
import com.example.seat_jpa.param.UserQueryParam;
import com.example.seat_jpa.service.LoginService;
import com.example.seat_jpa.service.UserService;
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
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private LoginService loginService;

    @Autowired
    public UserController setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

    @Autowired
    public UserController setLoginService(LoginService loginService) {
        this.loginService = loginService;
        return this;
    }

    @PostMapping("/login")
    public ApiResult<String> userLogin(@NotNull UserLoginParam param){
        var response = loginService.login(param.getUsername(), param.getPassword(), param.getIsAdmin());
        ApiResult<String> res;
        res=switch (response){
            case ACCOUNT_NOT_FOUND -> ApiResult.get(501,null,"用户名未找到");
            case ERROR_PASSWORD -> ApiResult.get(502,null,"密码错误");
            case SUCCESS -> ApiResult.ok(StpUtil.getTokenValue());
            case AUTHENTICATION_ERROR -> ApiResult.get(503,null,"认证错误");
        };
        return res;
    }

    @PostMapping("/enroll")
    public ApiResult<Integer>userEnroll(@NotNull UserEnrollParam param){
        var id = userService.enroll(param.getUsername(), param.getPassword(), param.getIsAdmin());
        return ApiResult.ok(id);
    }

    @PostMapping("/query")
    public ApiResult<Page<User>>findUserInFilter(@NotNull UserQueryParam param){
        var filter = userService.findUserInFilter(param);
        return ApiResult.ok(filter);
    }
}
