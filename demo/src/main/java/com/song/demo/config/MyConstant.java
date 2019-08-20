package com.song.demo.config;

/**
 * @ClassName: MyConstant
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/8/19 10:35
 * @Version: 1.0
 **/
public enum  MyConstant {

    SUCCESS(10000,"操作成功"),
    MESSAGE_IS_NULL(10012,"返回信息为NULL"),
    NULL_POINTER_EXCEPTION(10015,"必要参数不能为空"),
    ILLEGAL_REQUEST_PARAMETERS(10017,"非法的请求参数");


    private final Integer code;
    private final String message;

    MyConstant(Integer code,String message){
        this.code=code;
        this.message=message;
    }


    public Integer getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }


}
