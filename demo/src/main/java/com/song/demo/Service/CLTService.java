package com.song.demo.Service;

public interface CLTService {

    String realLogin(String phone,String pwd);

    String alive(String phone);

    String prepay(String totalAmount,String phone) throws Exception;

    String jmccPayStatus(String outTradeNo,String phone);

}
