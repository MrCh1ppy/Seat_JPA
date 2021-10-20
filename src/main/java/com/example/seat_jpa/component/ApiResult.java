package com.example.seat_jpa.component;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author 橙鼠鼠
 */
public record ApiResult<T>(Integer code, T data, String msg) {

    @Contract("_ -> new")
    public static <T> @NotNull ApiResult<T> ok(T data){
        return new ApiResult<>(200,data,"操作成功");
    }

    @NotNull
    @Contract("_, _, _ -> new")
    public static <T>ApiResult<T> error(T data, Integer code, String msg){
        return new ApiResult<>(code,data,msg);
    }

    @Contract("_, _, _ -> new")
    public static <T> @NotNull ApiResult<T> get(Integer code, T data, String msg){
        return new ApiResult<>(code,data,msg);
    }
}
