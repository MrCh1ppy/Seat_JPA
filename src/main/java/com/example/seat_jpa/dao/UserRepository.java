package com.example.seat_jpa.dao;

import com.example.seat_jpa.entity.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 橙鼠鼠
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserNameAndPassword(String username, String password);
    User findByUserName(String username);
}