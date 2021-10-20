package com.example.seat_jpa.component;

import cn.dev33.satoken.stp.StpInterface;
import com.example.seat_jpa.dao.UserRepository;
import com.example.seat_jpa.project.struct.LoginID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author 橙鼠鼠
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    private UserRepository userRepository;

    @Autowired
    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        if(o instanceof LoginID loginID){
            return loginID.isAdmin()?List.of("admin"):Collections.emptyList();
        }
        return Collections.emptyList();
    }
}
