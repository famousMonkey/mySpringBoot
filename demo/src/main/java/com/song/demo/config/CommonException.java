package com.song.demo.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName: CommonException
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/6/25 16:31
 * @Version: 1.0
 **/
@RestControllerAdvice
public class CommonException {

    @ExceptionHandler(value = Exception.class)
    public String myException(){
        return "我的异常111";
    }

    @ExceptionHandler(value = NullPointerException.class)
    public String myNullPointerException(){
        return "我的空指针异常";
    }


}
