package com.song.demo.Service;

public interface CLTService {

    String realLogin(String phone,String pwd);

    String prepay(String totalAmount) throws Exception;

    String jmccPayStatus(String outTradeNo );

}
