package com.example.seat_jpa.project.exception;

import org.jetbrains.annotations.Contract;

/**
 * @author 橙鼠鼠
 */

public enum ErrorInfoEnum {
    /**错误信息
     * */
    SUCCESS(200),
    PROJECT_ERROR(501),
    VALIDATION_ERROR(502),
    CRUD_ERROR(503),
    ERROR_NOT_INCLUDED_ERROR(504)
    ;

    Integer errorCode;
    @Contract(pure = true)
    ErrorInfoEnum(int errorCode) {
        this.errorCode=errorCode;
    }
}
