package com.song.demo.Service;

import com.song.demo.constant.Result;

public interface DuolabaoService {

    Result payCreate(String requestNum);
    Result createPayUrl(String requestNum);
    Result refund(String requestNum);
    Result refundPart(String requestNum,String refundPartAmount);
    Result payResult(String requestNum);

}
