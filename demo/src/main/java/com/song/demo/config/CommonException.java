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
        return "WOW--异常";
    }

    @ExceptionHandler(value = NullPointerException.class)
    public String myNullPointerException(){

        return "WOW--空指针";
    }


}
