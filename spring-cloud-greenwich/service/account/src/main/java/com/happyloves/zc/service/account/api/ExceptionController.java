package com.happyloves.zc.service.account.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author 赵小胖
 * @Date 2019/9/23 21:58
 * @Description:
 */
@Slf4j
@RestControllerAdvice
public class ExceptionController {

    /**
     * 校验错误拦截处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String apiExceptionHandler(MethodArgumentNotValidException ex) {
        log.error("ApiException 异常抛出：{}", ex);
        return ex.getMessage();
    }
}
