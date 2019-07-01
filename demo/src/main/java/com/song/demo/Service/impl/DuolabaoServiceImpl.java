package com.song.demo.Service.impl;

import com.alibaba.fastjson.JSON;
import com.song.demo.Service.DuolabaoService;
import com.song.demo.constant.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
    public Result payCreate(String requestNum) {
        String param = createParam(requestNum);
        String url="https://openapi.duolabao.com/v1/customer/order/pay/create";
        Map response = sendRequest(param, url);
        log.info("支付订单返回结果:"+response.toString());
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

    @Override
    public Result createPayUrl(String requestNum) {
        String urlParam = createUrlParam(requestNum);
        String url="https://openapi.duolabao.com/v1/customer/order/payurl/create";
        Map response = sendRequest(urlParam, url);
        log.info("支付链接返回结果:"+response.toString());
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
            return new Result(true,map.get("url").toString());
        }
    }

    @Override
    public Result refund(String requestNum) {
        String param = createRefundParam(requestNum);
        String url="https://openapi.duolabao.com/v1/customer/order/refund";
        Map response = sendRequest(param, url);
        log.info("退款返回结果:"+response.toString());
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

    @Override
    public Result refundPart(String requestNum, String refundPartAmount) {
        String param = createRefundPartParam(requestNum, refundPartAmount);
        String url="https://openapi.duolabao.com/v1/customer/order/refund/part";
        Map response = sendRequest(param, url);
        log.info("部分退款返回结果:"+response.toString());
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

    @Override
    public Result payResult(String requestNum) {
        Map<String, String> request = createPayResultParam(requestNum);
        String url="https://openapi.duolabao.com/v1/customer/order/payresult";
        Map response = sendQueryRequest(request, url);
        log.info("支付结果查询返回结果:"+response.toString());
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
            return new Result(true,map.get("status").toString());
        }
    }


    /**
     * @Author 宋正健
     * @Description //TODO(创建订单支付结果查询参数)
     * @Date 2019/6/29 11:43
     * @Param [requestNum]
     * @Return java.lang.String
     */
    public Map<String,String> createPayResultParam(String requestNum){
        Map<String,String> param=new HashMap<>();
        param.put("customerNum","10001114596730086163709");
        param.put("shopNum","10001214641783052104486");
        param.put("requestNum",requestNum);
        return param;
    }



    /**
     * @Author 宋正健
     * @Description //TODO(创建部分退款参数)
     * @Date 2019/6/29 11:38
     * @Param [requestNum, refundPartAmount]
     * @Return java.lang.String
     */
    private String createRefundPartParam(String requestNum,String refundPartAmount){
        Map<String,String> map=new HashMap<>();
        map.put("customerNum","10001114596730086163709");
        map.put("shopNum","10001214641783052104486");
        map.put("requestNum",requestNum);
        map.put("refundPartAmount",refundPartAmount);
        return JSON.toJSONString(map);
    }


    /**
     * @Author 宋正健
     * @Description //TODO(创建全部退款参数)
     * @Date 2019/6/29 11:18
     * @Param [requestNum]
     * @Return java.lang.String
     */
    private String createRefundParam(String requestNum){
        Map<String,String> map=new HashMap<>();
        map.put("customerNum","10001114596730086163709");
        map.put("shopNum","10001214641783052104486");
        map.put("requestNum",requestNum);
        return JSON.toJSONString(map);
    }


    /**
     * @Author 宋正健
     * @Description //TODO(创建哆啦宝支付连接参数)
     * @Date 2019/6/29 10:24
     * @Param [requestNum]
     * @Return java.lang.String
     */
    private String createUrlParam(String requestNum){
        Map<String,String> map=new HashMap<>();
        map.put("customerNum","10001114596730086163709");
        map.put("shopNum","10001214641783052104486");
        map.put("requestNum",requestNum);
        map.put("source","API");
        map.put("amount","1.00");
        map.put("callbackUrl","openapi.duolabao.com/duolaba/myNotify");//(可选)交易完成后，会调用此地址通知交易结果(目前只有交易成功会通知)
        return JSON.toJSONString(map);
    }



    /**
     * @Author 宋正健
     * @Description //TODO(创建哆啦宝支付订单参数)
     * @Date 2019/6/29 10:08
     * @Param []
     * @Return java.lang.String
     */
    private String createParam(String requestNum){
        Map<String,String> map=new HashMap<>();
        map.put("customerNum","10001114596730086163709");
        map.put("shopNum","10001214641783052104486");
        map.put("requestNum",requestNum);
        map.put("amount","1.00");
        map.put("bankType","ALIPAY");
        map.put("authId","ojiuXuGrrejS0HwGkU8R_R2MKjY8");
        map.put("callbackUrl","https://www.duolabao.com/duolaba/myNotify");
        return JSON.toJSONString(map);
    }


    /**
     * @Author 宋正健
     * @Description //TODO(发送哆啦宝支付请求)
     * @Date 2019/6/29 10:08
     * @Param [param, url]
     * @Return java.util.Map
     */
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
        return myRes;
    }

    private Map sendQueryRequest(Map<String,String> param,String url){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String myUrl=url+"/"+param.get("customerNum")+"/"+param.get("shopNum")+"/"+param.get("requestNum");
        log.info("\n查询结果请求地址：\n"+myUrl+"\n");
        HttpGet httpGet=new HttpGet(myUrl);
        httpGet.addHeader("accessKey","fe02aa7e8f3249fe86c93774b7103d9945f80");
        httpGet.addHeader("token","C9EF9BCE7138E21A353DE3F57187DC5E5396DD96");
        Long time = new Date().getTime();
        httpGet.addHeader("timestamp",time.toString());
        httpGet.addHeader("Content-Type","application/json");
        CloseableHttpResponse response = null;
        Map myRes = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String context = EntityUtils.toString(entity, "UTF-8");
            myRes = JSON.parseObject(context,Map.class);
            log.info("请求结果:\n" + context);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                httpGet.abort();
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return myRes;
    }
}
