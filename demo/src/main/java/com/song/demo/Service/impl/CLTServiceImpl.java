package com.song.demo.Service.impl;

import com.alibaba.fastjson.JSON;
import com.song.demo.Service.CLTService;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: CLTServiceImpl
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/19 11:46
 * @Version: 1.0
 **/
@Service
@Slf4j
public class CLTServiceImpl implements CLTService {
    @Override
    public String realLogin(String phone, String pwd) {
        Map<String,String> request=new HashMap<>();
        request.put("phone",phone);
        request.put("password",pwd);
        request.put("type","0");
        String param = JSON.toJSONString(request);
        String url="https://api/account/realLogin";
        Map<String, String> response = sendRequest(url, param);
        return JSON.toJSONString(response);
    }


    private Map<String,String> sendRequest(String url,String param){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        StringEntity body = new StringEntity(param, "utf-8");
        httpPost.setEntity(body);
        CloseableHttpResponse response = null;
        Map myRes = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String context = EntityUtils.toString(entity, "UTF-8");
            Map<String,String> data = (Map) JSON.parseObject(context).get("data");
            log.info("获取JSON：\n"+data);
            myRes = JSON.parseObject(context,Map.class);
            log.info("请求结果:\n" + context);
            return myRes;
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
        return myRes;
    }

}
