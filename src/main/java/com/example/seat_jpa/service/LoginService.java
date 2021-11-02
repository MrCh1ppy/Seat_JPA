package com.example.seat_jpa.service;

import com.example.seat_jpa.entity.enum_package.LoginResponse;

/**
 * @author 橙鼠鼠
 */
public interface LoginService {
     LoginResponse login(String username, String password,Boolean isAdmin);
}
