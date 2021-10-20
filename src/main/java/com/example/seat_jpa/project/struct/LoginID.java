package com.example.seat_jpa.project.struct;

import lombok.Data;

/**
 * @author 橙鼠鼠
 */
@Data
public class LoginID extends Object {
    private final int userId;
    private final boolean isAdmin;
}
