package com.example.seat_jpa.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.example.seat_jpa.dao.UserRepository;
import com.example.seat_jpa.entity.po.User;
import com.example.seat_jpa.param.UserQueryParam;
import com.example.seat_jpa.project.exception.ErrorInfoEnum;
import com.example.seat_jpa.project.exception.ProjectException;
import com.example.seat_jpa.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

/**
 * @author 橙鼠鼠
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        return this;
    }


    @Override
    public Integer enroll(String username, String password, boolean forAdmin) {
        if(userRepository.findByUserName(username)!=null){
            throw new ProjectException("重复用户名,不得注册", ErrorInfoEnum.PROJECT_ERROR);
        }
        var user = new User.Builder()
                .setPassword(SaSecureUtil.md5(password))
                .setIsAdmin(forAdmin)
                .setUsername(username)
                .build();
        var user1 = userRepository.save(user);
        log.info("save user{}",user);
        return user1.getId();
    }

    @Override
    public Page<User> findUserInFilter(UserQueryParam param) {
        var builder = new User.Builder();
        if(param.getIsAdmin()!=null){
            builder.setIsAdmin(param.getIsAdmin());
        }
        if(!param.getUsername().isBlank()){
            builder.setUsername(param.getUsername());
        }
        var target = builder.build();
        var matcher = ExampleMatcher.matching()
                .withMatcher("userName", ExampleMatcher.GenericPropertyMatchers.contains());
        var userExample = Example.of(target, matcher);
        return userRepository.findAll(userExample, param.toPageRequest());
    }
}
