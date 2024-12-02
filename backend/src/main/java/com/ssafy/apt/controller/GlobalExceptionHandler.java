package com.ssafy.apt.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("error", "Message: " + e.getMessage());
        return "error";
    }
}
/*
@ControllerAdvice
=> @Component 어노테이션의 특수한 케이스로 스프링 애플리케이션에서 전역적으로 예외를 핸들링할 수 있게 해주는 어노테이션이다.

@ExceptionHandler
=> @ExceptionHandler 어노테이션을 사용하면 value로 원하는 예외를 지정하고 이를 핸들링 할 수 있다.
*/