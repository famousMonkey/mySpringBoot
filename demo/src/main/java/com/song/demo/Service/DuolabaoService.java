package com.song.demo.Service;

import com.song.demo.constant.Result;

import java.util.Map;

public interface DuolabaoService {

    Map<String,String> payCreate(String requestNum,String authId);
    Result createPayUrl(String requestNum,String amount);
    Result refund(String requestNum);
    Result refundPart(String requestNum,String refundPartAmount);
    Result payResult(String requestNum);
    Result close(String requestNum);
    Result cancel(String requestNum);
    Result passive(String requestNum,String authCode,String amount);

}
