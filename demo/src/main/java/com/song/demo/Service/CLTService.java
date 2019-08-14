package com.song.demo.Service;

import java.util.Map;

public interface CLTService {

    Map realLogin(String phone,String pwd);

    Map alive(String phone);

    Map prepay(String totalAmount,String phone,String qrCode) throws Exception;

    Map jmccPayStatus(String outTradeNo, String phone);

}
