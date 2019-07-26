package com.song.demo.Service;

import java.util.Map;

public interface TLService {

    Map<String,String> authcodeTouserid(String authCode,String subAppId) throws Exception;

    Map<String,String> pay(String total,String openId) throws Exception;

}
