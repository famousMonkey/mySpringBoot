package com.song.demo.config;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: ResultMsg
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/8/19 10:29
 * @Version: 1.0
 **/
@Data
public class ResultMsg<T> implements Serializable {

    private Integer code;

    private String message;

    private T data;

    public ResultMsg(){}

    public ResultMsg(Integer code,String message){
        this.code=code;
        this.message=message;
    }

    public ResultMsg(Integer code,String message,T data){
        this.code=code;
        this.message=message;
        this.data=data;
    }

}
