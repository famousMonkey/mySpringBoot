package com.song.demo.Service.impl;

import com.song.demo.Service.TLService;
import com.song.demo.util.HttpConnectionUtil;
import com.song.demo.util.SybUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName: TLServiceImpl
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/23 11:23
 * @Version: 1.0
 **/
@Service
@Slf4j
public class TLServiceImpl implements TLService {


    @Override
    public Map<String, String> authcodeTouserid(String authCode,String subAppId) throws Exception {
        HttpConnectionUtil http = new HttpConnectionUtil("https://vsp.allinpay.com/apiweb/unitorder/authcodetouserid");
        http.init();
        byte[] bytes = http.postParams(createAuthCodeToUserIdParam(authCode,subAppId), true);
        String s = new String(bytes, "UTF-8");
        Map<String, String> response = handleResult(s, "123456");
        return response;
    }

    private Map<String,String> createAuthCodeToUserIdParam(String authCode,String subAppId) throws Exception {
        TreeMap<String,String> param=new TreeMap<>();
        param.put("cusid","560455055410NZ8");
        param.put("appid","00170396");
        param.put("version","12");
        param.put("authcode",authCode);
        param.put("authtype","01");//01:微信付款码  02:银联userAuth
        param.put("sub_appid",subAppId);
        param.put("randomstr", SybUtil.getValidatecode(8));
        param.put("signtype","MD5");
        param.put("sign",SybUtil.sign(param,"123456"));
        return param;
    }


    private Map<String,String> handleResult(String result,String merchantSecret) throws Exception {
        Map map = SybUtil.json2Obj(result, Map.class);
        log.info("结果转map[]","123");
        return map;
    }

}
