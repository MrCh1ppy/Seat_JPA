package com.example.seat_jpa;

import com.example.seat_jpa.controller.SeatController;
import com.example.seat_jpa.controller.UserController;
import com.example.seat_jpa.controller.ZoneController;
import com.example.seat_jpa.param.SeatAddParam;
import com.example.seat_jpa.param.UserEnrollParam;
import com.example.seat_jpa.param.UserLoginParam;
import com.example.seat_jpa.param.ZoneAddParam;
import com.example.seat_jpa.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class SeatJpaApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private UserController userController;
    @Autowired
    private ZoneController zoneController;
    @Autowired
    private SeatController seatController;



    @Test
    void name() {
        var param = new UserLoginParam("root", "root", true);
        var login = userController.userLogin(param);
        System.out.println(login);
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    void addZoneAndUser(){
        var param = new UserEnrollParam(
                "admin",
                "admin",
                true
        );
        var enroll = userController.userEnroll(param);
        System.out.println(enroll);
        if(enroll.data() != null){
            var param1 = new ZoneAddParam(
                    "无",
                    "9309",
                    enroll.data()
            );
            var zone = zoneController.addZone(param1);
            System.out.println(zone);
        }
        if(enroll.data()!=null){
            var userLoginParam = new UserLoginParam(
                    "admin",
                    "admin",
                    true
            );
            var login = userController.userLogin(userLoginParam);
            System.out.println(login);
        }
    }

    @Test
    void addSeat(){
        var param = new SeatAddParam(
                "座位描述1",
                16
        );
        var result = seatController.addSeat(param);
        System.out.println(result);
    }

}
