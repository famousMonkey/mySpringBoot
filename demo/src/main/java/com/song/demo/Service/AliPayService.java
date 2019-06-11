package com.song.demo.Service;

public interface AliPayService {

    public Boolean precreate(String orderId,String totalAmount);

    public Boolean pay(String orderId,String authCode,String totalAmount);

}
