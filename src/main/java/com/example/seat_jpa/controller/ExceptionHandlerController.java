package com.example.seat_jpa.controller;

import com.example.seat_jpa.component.ApiResult;
import com.example.seat_jpa.project.exception.ProjectException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.datetime.standard.DateTimeFormatterFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * @author 橙鼠鼠
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    /**
     * 解析以获取错误位置
     */
    private @NotNull String getErrorPosition (@NotNull Exception e) {
        if (e.getStackTrace().length > 0) {
            final var element = e.getStackTrace()[0];
            String fileName = element.getFileName() == null ? "未找到错误文件" : element.getFileName();
            final var lineNumber = element.getLineNumber();
            return fileName + ":" + lineNumber;
        } else {
            return "";
        }
    }

    private void loggerOutPut (String exceptionName, String exceptionPosition, String exceptionCode) {
        String text = "////////ERROR////////" +
                "\nlocation: " +
                exceptionPosition +
                "\nname: " +
                exceptionName +
                "\ncode: " +
                exceptionCode +
                "\ntime" +
                LocalDateTime.now().format(new DateTimeFormatterFactory("yyyy年MM月dd日 HH:mm:ss").createDateTimeFormatter());
        log.error(text);
    }

    ApiResult<String> defaultHandler(Exception e, Integer failCode, String failMsg){
        var position = getErrorPosition(e);
        loggerOutPut(failMsg,position, String.valueOf(666));
        e.printStackTrace();
        return ApiResult.error(position,failCode,failMsg);
    }

    @ExceptionHandler(value = Exception.class)
    ApiResult<String> exceptionHandler (@NotNull Exception e) {
        var errorMsg = e.getMessage() != null ? e.getMessage() : "未知错误";
        return defaultHandler(e, 666, errorMsg);
    }

    @ExceptionHandler(value = ProjectException.class)
    ApiResult<String> projectExceptionHandler(ProjectException e){
        return defaultHandler(e,e.getCode(),e.getMessage());
    }


}
