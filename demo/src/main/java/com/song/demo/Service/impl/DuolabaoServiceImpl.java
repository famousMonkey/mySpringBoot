package com.song.demo.Service.impl;

import com.alibaba.fastjson.JSON;
import com.song.demo.Service.DuolabaoService;
import com.song.demo.constant.Result;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: DuolabaoServiceImpl
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/6/27 14:45
 * @Version: 1.0
 **/
@Service
@Slf4j
public class DuolabaoServiceImpl implements DuolabaoService {
    @Override
    public Result payCreate() {
        String param = createParam();
        String url="https://openapi.duolabao.com/v1/customer/order/pay/create";
        Map response = sendRequest(param, url);
        log.info("2-返回结果:"+response.toString());
        if("fail".equalsIgnoreCase(response.get("result").toString())){
            log.info("》》》》失败====");
            String error = response.get("error").toString();
            Map map=(Map) JSON.parse(error);
            return new Result(false,map.get("errorMsg").toString());
        }else if("error".equalsIgnoreCase(response.get("result").toString())){
            log.info("》》》》异常====");
            String error = response.get("error").toString();
            Map map=(Map) JSON.parse(error);
            return new Result(false,map.get("errorCode").toString());
        }else{
            log.info("》》》》成功====");
            String data = response.get("data").toString();
            Map map=(Map) JSON.parse(data);
            return new Result(true,map.get("orderNum").toString());
        }
    }


    private String createParam(){
        Map<String,String> map=new HashMap<>();
        map.put("customerNum","10001114596730086163709");
        map.put("shopNum","10001214641783052104486");
        map.put("requestNum","315080832507485");
        map.put("amount","1.00");
        map.put("bankType","ALIPAY");
        map.put("authId","ojiuXuGrrejS0HwGkU8R_R2MKjY8");
        map.put("callbackUrl","https://www.duolabao.com");
        return JSON.toJSONString(map);
    }

    private Map sendRequest(String param,String url){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        StringEntity body = new StringEntity(param, "utf-8");
        httpPost.setEntity(body);
        httpPost.addHeader("accessKey","fe02aa7e8f3249fe86c93774b7103d9945f80");
        httpPost.addHeader("token","C9EF9BCE7138E21A353DE3F57187DC5E5396DD96");
        Long time = new Date().getTime();
        httpPost.addHeader("timestamp",time.toString());
        httpPost.addHeader("Content-Type","application/json");
        CloseableHttpResponse response = null;
        Map myRes = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String context = EntityUtils.toString(entity, "UTF-8");
            myRes = JSON.parseObject(context,Map.class);
            log.info("请求结果:\n" + context);
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
        log.info("1-返回结果:"+myRes.toString());
        return myRes;
    }
}
