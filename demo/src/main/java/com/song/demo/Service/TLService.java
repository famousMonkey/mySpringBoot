package com.song.demo.Service;

import com.song.demo.config.ResultMsg;

import java.math.BigDecimal;
import java.util.Map;

public interface TLService {

    Map<String,String> authcodeTouserid(String authCode,String subAppId) throws Exception;

    ResultMsg pay(String total, String openId) throws Exception;

    Map<String,String> scanqrpay(BigDecimal trxamt, String reqsn, String authcode) throws Exception;

}
