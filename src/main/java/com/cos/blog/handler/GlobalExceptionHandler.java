package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value=IllegalArgumentException.class) //IllegalArgumentException이 발생하면 아래 함수로 (Spring이)전달해 준다.
    public String handleArgumentException(IllegalArgumentException e) {
        return "<h1>"+e.getMessage()+"</h1>";
    }

    @ExceptionHandler(value=Exception.class) //최고 부모인 Exception을 넣어주면 모든 예외가 이쪽으로 들어온다. 하지만 상세한 구분을 할 수 없다.
    public String handleArgumentException(Exception e) {
        return "<h1>"+e.getMessage()+"</h1>";
    }
}
