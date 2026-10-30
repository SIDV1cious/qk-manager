package com.itheima.handler;

import com.itheima.common.Result;
import com.itheima.exception.QkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j  //日志
@RestControllerAdvice //异常处理器-->监控所有Controller抛出的异常
@Component //Spring容器
public class GlobalExceptionHandler {

    @ExceptionHandler(QkException.class) //捕获我们自己throw抛出的异常
    public Result handlerQkException(QkException e) {
        log.error("业务异常，异常信息：" + e.getMessage());
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class) //捕获由框架抛出的其他异常
    public Result handlerException(Exception e) {
        e.printStackTrace();
        log.error("程序运行时出现异常，异常信息：" + e.getMessage());
        return Result.error("程序运行出现异常，请重试！");
    }
}
