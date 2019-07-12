package com.song.demo.Service.impl;

import com.alibaba.fastjson.JSON;
import com.song.demo.Service.DuolabaoService;
import com.song.demo.config.DlbConfig;
import com.song.demo.constant.Result;
import com.song.demo.util.SHAUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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


    @Autowired
    private DlbConfig dlbConfig;

    @Override
    public Map payCreate(String requestNum,String authId) {
        String param = createParam(requestNum,authId);
        String url="/v1/customer/order/pay/create";
        Map response = sendRequest(param, url);
        log.info("支付订单返回结果:"+response.toString());
/*        if(StringUtils.isNotBlank(response.get("error").toString())){
            String s = response.get("error").toString();
            Map map=(Map) JSON.parse(s);
            return new Result(false,map.get("errorMsg").toString());
        }*/
        if("fail".equalsIgnoreCase(response.get("result").toString())){
            log.info("》》》》失败====");
            String error = response.get("error").toString();
            Map map=(Map) JSON.parse(error);
            map.put("status","Fail");
            //return new Result(false,map.get("errorMsg").toString());
            return map;
        }else if("error".equalsIgnoreCase(response.get("result").toString())){
            log.info("》》》》异常====");
            String error = response.get("error").toString();
            Map map=(Map) JSON.parse(error);
            map.put("status","Error");
            //return new Result(false,map.get("errorCode").toString());
            return map;
        }else{
            log.info("》》》》成功====");
            String data = response.get("data").toString();
            Map map=(Map) JSON.parse(data);
            map.put("status","Success");
            //return new Result(true,map.get("bankRequest").toString());
            return map;
        }
    }

    @Override
    public Result createPayUrl(String requestNum,String amount) {
        String urlParam = createUrlParam(requestNum,amount);
        String url="/v1/customer/order/payurl/create";
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
        String url="/v1/customer/order/refund";
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
        String url="/v1/customer/order/refund/part";
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
        String url="/v1/customer/order/payresult";
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


    @Override
    public Result close(String requestNum) {
        String request = createCloseParam(requestNum);
        String url="/v1/customer/order/close";
        Map response = sendRequest(request, url);
        log.info("关闭返回结果:"+response.toString());
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
    public Result cancel(String requestNum) {
        String request = createCancelParam(requestNum);
        String url="/v2/customer/order/cancel";
        Map response = sendRequest(request, url);
        log.info("撤销返回结果:"+response.toString());
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
    public Result passive(String requestNum, String authCode, String amount) {
        String request = createAuthCodeParam(requestNum, authCode, amount);
        String url="/v1/customer/passive/create";
        Map response = sendRequest(request, url);
        log.info("哆啦宝付款码支付结果:"+response.toString());
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
            //查询支付结果
            Map myMap = queryPassive(requestNum);
            return new Result(true,myMap.get("message").toString());
        }
    }


    /**
     * @Author 宋正健
     * @Description //TODO(查询付款码支付结果并根据结果返回)
     * @Date 2019/7/9 14:02
     * @Param [requestNum]
     * @Return java.util.Map
     */
    private Map queryPassive(String requestNum){
        Map<String,String> resultMsg=new HashMap<>();
        Map<String, String> request = createPayResultParam(requestNum);
        String url="/v1/customer/order/payresult";
        int i=0;
        while(true){
            Map response = sendQueryRequest(request, url);
            String result = (String)response.get("result");
            log.info("查询哆啦宝付款码支付连接结果== "+result);
            try {
                TimeUnit.SECONDS.sleep(3);
                ++i;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if("success".equalsIgnoreCase(result)){
                Map<String,String> data = (Map)response.get("data");
                log.info("哆啦宝付款码支付查询返回数据：\n"+data);
                String myStatus = data.get("status");
                log.info("支付结果状态："+myStatus);
                if("SUCCESS".equalsIgnoreCase(myStatus)){
                    log.info("支付完成...");
                    resultMsg.put("status","success");
                    resultMsg.put("message","支付成功");
                    return resultMsg;
                }else if("INIT".equalsIgnoreCase(myStatus)){
                    log.info("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n"+i+"\n>>>>>>>>>>>>>>>>>>>>>>>>>>>\n");
                    if(i==6){
                        log.info("支付超时...");
                        resultMsg.put("status","fail");
                        resultMsg.put("message","支付超时，请重新支付");
                        return resultMsg;
                    }else{
                        log.info("支付中...");
                        continue;
                    }
                }else {
                    log.info("其他情况...");
                    resultMsg.put("status","fail");
                    resultMsg.put("message",data.get("remark"));
                    return resultMsg;
                }
            }else {
                resultMsg.put("status","fail");
                resultMsg.put("message","获取付款码支付结果异常");
                return resultMsg;
            }
        }

    }


    /**
     * @Author 宋正健
     * @Description //TODO(撤销参数创建)
     * @Date 2019/7/1 11:22
     * @Param [requestNum]
     * @Return java.lang.String
     */
    private String createCancelParam(String requestNum){
        Map<String,String> param=new HashMap<>();
        param.put("customerNum",dlbConfig.getCustomerNum());
        param.put("requestNum",requestNum);
        param.put("bankRequestNum","10031115009669622861143");
        return JSON.toJSONString(param);
    }



    /**
     * @Author 宋正健
     * @Description //TODO(关闭参数创建)
     * @Date 2019/7/1 11:20
     * @Param [requestNum]
     * @Return java.lang.String
     */
    private String createCloseParam(String requestNum){
        Map<String,String> param=new HashMap<>();
        param.put("customerNum",dlbConfig.getCustomerNum());
        param.put("requestNum",requestNum);
        param.put("bankRequestNum","10031115009669622861143");
        return JSON.toJSONString(param);
    }


    /**
     * @Author 宋正健
     * @Description //TODO(创建订单支付结果查询参数)
     * @Date 2019/6/29 11:43
     * @Param [requestNum]
     * @Return java.lang.String
     */
    private Map<String,String> createPayResultParam(String requestNum){
        Map<String,String> param=new HashMap<>();
        param.put("customerNum",dlbConfig.getCustomerNum());
        param.put("shopNum",dlbConfig.getShopNum());
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
        map.put("customerNum",dlbConfig.getCustomerNum());
        map.put("shopNum",dlbConfig.getShopNum());
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
        map.put("customerNum",dlbConfig.getCustomerNum());
        map.put("shopNum",dlbConfig.getShopNum());
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
    private String createUrlParam(String requestNum,String amount){
        Map<String,String> map=new HashMap<>();
        map.put("customerNum",dlbConfig.getCustomerNum());
        map.put("shopNum",dlbConfig.getShopNum());
        map.put("requestNum",requestNum);
        map.put("source","API");
        map.put("amount",amount);
        map.put("callbackUrl",dlbConfig.getCallbackUrl());//(可选)交易完成后，会调用此地址通知交易结果(目前只有交易成功会通知)
        return JSON.toJSONString(map);
    }



    /**
     * @Author 宋正健
     * @Description //TODO(创建哆啦宝支付订单参数)
     * @Date 2019/6/29 10:08
     * @Param []
     * @Return java.lang.String
     */
    private String createParam(String requestNum,String authId){
        Map<String,String> map=new HashMap<>();
        map.put("customerNum",dlbConfig.getCustomerNum());
        map.put("shopNum",dlbConfig.getShopNum());
        map.put("requestNum",requestNum);
        map.put("amount","0.01");
        map.put("bankType","WX_XCX");
        map.put("authId",authId);
        map.put("callbackUrl",dlbConfig.getCallbackUrl());
        return JSON.toJSONString(map);
    }

    /**
     * @Author 宋正健
     * @Description //TODO(创建哆啦宝商家被扫参数)
     * @Date 2019/7/9 10:05
     * @Param [requestNum, authCode, amount]
     * @Return java.lang.String
     */
    private String createAuthCodeParam(String requestNum,String authCode,String amount){
        Map<String,String> map=new HashMap<>();
        map.put("customerNum",dlbConfig.getCustomerNum());
        map.put("shopNum",dlbConfig.getShopNum());
        map.put("requestNum",requestNum);
        map.put("amount",amount);
        map.put("authCode",authCode);
        map.put("source","API");
        return JSON.toJSONString(map);
    }


    /**
     * @Author 宋正健
     * @Description //TODO(发送哆啦宝支付请求)
     * @Date 2019/6/29 10:08
     * @Param [param, url]
     * @Return java.util.Map
     */
    private Map sendRequest(String param,String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String myUrl="https://openapi.duolabao.com"+url;
        HttpPost httpPost = new HttpPost(myUrl);
        StringEntity body = new StringEntity(param, "utf-8");
        httpPost.setEntity(body);
        httpPost.addHeader("accessKey",dlbConfig.getAccessKey());
        Long time = new Date().getTime();
        httpPost.addHeader("timestamp",time.toString());
        String my="secretKey="+dlbConfig.getSecretKey()+"&timestamp="+time.toString()+"&path="+url+"&body="+param;
        System.out.println("==生成token所需字符串==\n"+my);
        String s = SHAUtils.SHA1(my).toUpperCase();
        System.out.println("==生成的token==\n"+s);
        httpPost.addHeader("token",s);
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

    /**
     * @Author 宋正健
     * @Description //TODO(发送哆啦宝查询请求)
     * @Date 2019/7/12 14:04
     * @Param [param, url]
     * @Return java.util.Map
     */
    private Map sendQueryRequest(Map<String,String> param,String url){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String myUrl="https://openapi.duolabao.com/"+url+"/"+param.get("customerNum")+"/"+param.get("shopNum")+"/"+param.get("requestNum");
        log.info("\n查询结果请求地址：\n"+myUrl+"\n");
        HttpGet httpGet=new HttpGet(myUrl);
        httpGet.addHeader("accessKey",dlbConfig.getAccessKey());
        Long time = new Date().getTime();
        httpGet.addHeader("timestamp",time.toString());
        String my="secretKey="+dlbConfig.getSecretKey()+"&timestamp="+time.toString()+"&path="+url+"/"+param.get("customerNum")+"/"+param.get("shopNum")+"/"+param.get("requestNum");
        System.out.println("==生成token所需字符串==\n"+my);
        String s = SHAUtils.SHA1(my).toUpperCase();
        System.out.println("==生成的token==\n"+s);
        httpGet.addHeader("token",s);
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
