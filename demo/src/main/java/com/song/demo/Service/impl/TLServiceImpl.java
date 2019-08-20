package com.song.demo.Service.impl;

import com.alibaba.fastjson.JSON;
import com.song.demo.Service.TLService;
import com.song.demo.util.HttpClientUtil;
import com.song.demo.util.HttpConnectionUtil;
import com.song.demo.util.MD5Util;
import com.song.demo.util.SybUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

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
        String url="https://vsp.allinpay.com/apiweb/unitorder/authcodetouserid";
        Map<String, String> authCodeToUserIdParam = createAuthCodeToUserIdParam(authCode, subAppId);
        return sendRequest(url,authCodeToUserIdParam);
    }

    @Override
    public Map<String, String> pay(String total, String openId) throws Exception {
        String url="https://vsp.allinpay.com/apiweb/unitorder/pay";
        Map<String, String> payParam = createPayParam(total, openId);
        return sendRequest(url,payParam);
    }

    @Override
    public Map<String, String> scanqrpay(BigDecimal trxamt, String reqsn, String authcode) throws Exception{
        String url="https://vsp.allinpay.com/apiweb/unitorder/scanqrpay";
        Map scanqrpayParam = createScanqrpayParam(trxamt, reqsn, authcode);
        String s = HttpClientUtil.doPost(url, scanqrpayParam);
        Map map = JSON.parseObject(s, Map.class);
        log.info("返回结果：{}",map);
        //return sendRequest(url,scanqrpayParam);
        return map;
    }

    private Map createScanqrpayParam(BigDecimal trxamt, String reqsn, String authcode) throws Exception{
        TreeMap<String,String> map=new TreeMap<>();
        map.put("cusid","560455055410NZ8");
        map.put("appid","00170396");
        map.put("version","11");
        map.put("randomstr",SybUtil.getValidatecode(8));
        String money = trxamt.multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_HALF_UP).toString();
        map.put("trxamt",money);
        map.put("reqsn",reqsn);
        map.put("authcode",authcode);
        //map.put("sign",SybUtil.sign(map,"123456"));
        map.put("sign",myMd5(map,"123456"));
        return map;
    }

    private Map<String,String> createAuthCodeToUserIdParam(String authCode, String subAppId) throws Exception {
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
        log.info("参数：{}",param);
        return param;
    }

    private Map<String,String> createPayParam(String total,String openId) throws Exception {
        TreeMap<String,String> param=new TreeMap<>();
        param.put("cusid","560455055410NZ8");
        param.put("appid","00170396");
        param.put("version","11");
        param.put("trxamt",total);
        param.put("reqsn", UUID.randomUUID().toString().replace("-",""));
        param.put("paytype","W06");
        param.put("randomstr",SybUtil.getValidatecode(8));
        param.put("body","测试");
        param.put("remark","这是一个测试");
        param.put("validtime","6");
        param.put("acct",openId);
        param.put("notify_url","http://meatball.org.cn");
        param.put("limit_pay","no_credit");
        param.put("sub_appid","wx92d6c777845694a4");
        param.put("signtype","MD5");
        param.put("sign",SybUtil.sign(param,"123456"));
       log.info("参数：{}",param);
       return param;
    }

    private Map<String,String> handleResult(String result) throws Exception {
        Map map = JSON.parseObject(result, Map.class);
        log.info("结果转map{}",map);
        return map;
    }

    private Map<String,String> sendRequest(String url,Map param) throws Exception {
        HttpConnectionUtil http = new HttpConnectionUtil(url);
        http.init();
        byte[] bytes = http.postParams(param, true);
        String s = new String(bytes, "UTF-8");
        return handleResult(s);
    }

    private Map<String,String> sendRequest2(String url,String param){
        long startTime = System.currentTimeMillis();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        StringEntity body = new StringEntity(param, "utf-8");
        httpPost.setHeader("Content-Type","application/json");
        httpPost.setEntity(body);
        CloseableHttpResponse response = null;
        log.info("请求地址：\n{}",url);
        log.info("请求参数：\n{}",param);
        Map map=null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String context = EntityUtils.toString(entity, "UTF-8");
            long endTime = System.currentTimeMillis();
            map = JSON.parseObject(context, Map.class);
            log.info("返回数据：\n{}.\n用时 = {}",map,(endTime-startTime));
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                httpPost.abort();
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }



    //加密签名字符串
    private String myMd5(Map map,String key){
        if(map.containsKey("sign")){
            map.remove("sign");
        }
        map.put("key",key);
        String myStr = getSignString(map);
        String sign = null;
        try {
            sign = MD5Util.md5(myStr.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.remove("key");
        return sign;
    }

    //生成签名字符串
    private String getSignString(Map map){
        Map order = MD5Util.order(map);
        Set<String> set = order.keySet();
        StringBuilder str=new StringBuilder();
        for (String s : set) {
            str.append(s+"="+order.get(s)+"&");
        }
        String myStr=str.substring(0,str.length()-1);
        log.info("签名字符串：{}",myStr);
        return myStr;
    }

}
