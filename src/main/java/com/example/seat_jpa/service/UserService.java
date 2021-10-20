package com.example.seat_jpa.service;


import com.example.seat_jpa.entity.po.User;
import com.example.seat_jpa.param.UserQueryParam;
import org.springframework.data.domain.Page;

/**
 * @author 橙鼠鼠
 */
public interface UserService {
    Integer enroll(String username,String password,boolean forAdmin);
    Page<User> findUserInFilter(UserQueryParam pram);
}
