package com.song.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: CommonException
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/6/25 16:31
 * @Version: 1.0
 **/
@Slf4j
@RestControllerAdvice
public class CommonException {

    @ExceptionHandler(value = Exception.class)
    public ResultMsg myException(Exception e, HttpServletRequest request){
       log.info("来了456");
        ResultMsg resultMsg=new ResultMsg(10022,e.getMessage(),request.getRequestURL());
        return resultMsg;
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResultMsg myNullPointerException(Exception e, HttpServletRequest request){
        log.info("789来了");
        ResultMsg resultMsg=new ResultMsg(10022,"必要参数不能为空",request.getRequestURL());
        return resultMsg;
    }


}
