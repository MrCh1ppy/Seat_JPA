package com.example.seat_jpa.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.example.seat_jpa.dao.UserRepository;
import com.example.seat_jpa.entity.enum_package.LoginResponse;
import com.example.seat_jpa.project.struct.LoginID;
import com.example.seat_jpa.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 橙鼠鼠
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    private UserRepository userRepository;

    @Autowired
    public LoginServiceImpl setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        return this;
    }

    @Override
    public LoginResponse login(String username, String password,Boolean isAdmin){
        var user = userRepository.findByUserName(username);
        if(user==null){
            return LoginResponse.ACCOUNT_NOT_FOUND;
        }
        if(!user.getPassword().equals(SaSecureUtil.md5(password))){
            return LoginResponse.ERROR_PASSWORD;
        }
        if(isAdmin!=null&&isAdmin.booleanValue()!=user.getIsAdmin().booleanValue()){
            return LoginResponse.AUTHENTICATION_ERROR;
        }
        var loginId = new LoginID(user.getId(), Boolean.TRUE.equals(isAdmin));
        StpUtil.login(loginId);
        return LoginResponse.SUCCESS;
    }
}
