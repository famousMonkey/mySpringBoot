package com.song.demo.Service;

public interface AliPayService {

    Boolean precreate(String orderId,String totalAmount);

    Boolean pay(String orderId,String authCode,String totalAmount);

    Boolean transfer(String orderId,String amount,String name);


}
