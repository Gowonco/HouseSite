package com.gowonco.house.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理器
 * @author gowonco
 * @date 2019-07-23 22:22
 */
@ControllerAdvice
public class ErrorHandler {
    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(value = {Exception.class,RuntimeException.class})
    public String error500(HttpServletRequest request,Exception e){
        log.error(e.getMessage(),e);
        log.error(request.getRequestURL()+"encounter 500");
        return "error/500";
    }
}
