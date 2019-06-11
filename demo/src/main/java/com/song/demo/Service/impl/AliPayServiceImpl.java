package com.song.demo.Service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.song.demo.Service.AliPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AliPayServiceImpl implements AliPayService {

    /*
     * @Author 宋正健
     * @Description //TODO(支付宝扫码支付)
     * @Date 2019/6/11 10:59
     * @Param [orderId, totalAmount]
     * @Return java.lang.Boolean
     */
    @Override
    public Boolean precreate(String orderId,String totalAmount){
        AlipayClient alipayClient  = createCommonParam();
        AlipayTradePrecreateRequest request = createPrecreateParam(orderId, totalAmount);
        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.execute(request);
            log.info("支付宝扫码支付返回：\n"+response.getBody());
            log.info("qr_Code："+response.getQrCode());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            return true;
        } else {
           return false;
        }
    }

    /*
     * @Author 宋正健
     * @Description //TODO(支付宝付款码支付)
     * @Date 2019/6/11 10:59
     * @Param [orderId, authCode, totalAmount]
     * @Return java.lang.Boolean
     */
    @Override
    public Boolean pay(String orderId, String authCode, String totalAmount) {
        AlipayClient alipayClient = createCommonParam();
        AlipayTradePayRequest request = createPayParam(orderId, authCode, totalAmount);
        AlipayTradePayResponse response = null;
        try {
            response = alipayClient.execute(request);
            log.info("付款码支付结果："+response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            return true;
        }else {
            int i=1;
            while(true){
                Integer result = query(orderId);
                if(1==result){
                    return true;
                }else if(2==result){
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("第"+i+"次查询支付结果");
                    ++i;
                    if(i==5){
                        return false;
                    }
                }else{
                    return false;
                }
            }
        }


    }

    private Integer query(String orderId){
        AlipayClient alipayClient = createCommonParam();
        AlipayTradeQueryRequest request = createQueryParam(orderId);
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
            log.info("查询结果：\n"+response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if("TRADE_SUCCESS".equalsIgnoreCase(response.getTradeStatus())){
            return 1;//支付成功
        }
        if("WAIT_BUYER_PAY".equalsIgnoreCase(response.getTradeStatus())){
            return 2;//等待用户支付
        }else{
            return 3;//其他
        }

    }


    /**
     * 创建扫码支付参数
     * @param orderId
     * @param totalAmount
     * @return
     */
    private AlipayTradePrecreateRequest createPrecreateParam(String orderId,String totalAmount){
        Map<String,String> param=new HashMap<>();
        param.put("out_trade_no",orderId);
        param.put("total_amount",totalAmount);
        param.put("subject","小海豚智慧油站");
        param.put("qr_code_timeout_express","90m");
        String s = JSON.toJSONString(param);
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setBizContent(s);
        return request;
    }

    /**
     * 创建付款码支付参数
     * @param orderId
     * @param totalAmount
     * @return
     */
    private AlipayTradePayRequest createPayParam(String orderId,String authCode,String totalAmount){
        Map<String,String> param=new HashMap<>();
        param.put("out_trade_no",orderId);
        param.put("scene","bar_code");
        param.put("auth_code",authCode);
        param.put("total_amount",totalAmount);
        param.put("subject","小海豚智慧油站");
        param.put("qtimeout_express","2m");

        String s = JSON.toJSONString(param);
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        request.setBizContent(s);
        return request;
    }

    /**
     * 创建查询参数
     * @param orderId
     * @return
     */
    private AlipayTradeQueryRequest createQueryParam(String orderId){
        Map<String,String> param=new HashMap<>();
        param.put("out_trade_no",orderId);
        String s = JSON.toJSONString(param);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent(s);
        return request;
    }

    /**
     * 创建公共参数
     * @return
     */
    private AlipayClient createCommonParam(){
        AlipayClient alipayClient =
                new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do",
                        "2016091900549124",
                        "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCVOs5aNZBXmWoXOpD85mV3M2ZobT2oGR8N0vA/KY3WE4GWivK97Bp3dEO36klJ9/0RP+lXcHZweV9k/zKKguPa0OFu1FF0erAVBi8qSe5avBID1gzbiVURpfECtDwrGyCOb9rNMrK8Fb3oFTxN1/7TV4Q487be+2c9k3/WQWHu9hT2WwCliorqpTTog/QS+TMRIISE0+WctyZTmcUFjRCyEme8k030H0FGX8nYDQ1imTfpqLJrzDf4q3Xlg8tRJIVs4OcGeDhz8uSH4C4M0RWoMMbXurPzSzmN2iZ/mpMuL4oHDcOwsBGrT5v0MgkwRDvBb3sriF9fjpqX9mPlxLVXAgMBAAECggEAIN+cd4jXLETydv2C1QNYFMMw6w4th+tIyP3PyMo5oZAYevZO9QGe7vOgu1IUJQRJLlSa7ZUrsik6l4AUPSkKUrizAHwrGXKxeWAsYa95n2lQuqGboX0GTWX1yrezjXdjW51OYOerMf3EIO0UpL1ROHNOAFHH1iUhoBHvFl6+2SnqFqPTzHRnHw+9JaLBI52iJIle1Rd0ZkS6W3y/IUosKLQLiEzyQbBZKOe1KGNKfXUDTLGKKG6rg8+D4IRh/Ga0dtDCCN4n2CEWQxCj3UqWYb/ACeul5HpE8Yyb5AB/BGVMUKD+Dtnsxs7Gk0+8gD5SWmSiOEzOLCPjH2bjkgn66QKBgQD2OeE22G1zoC3sea3wV5OYYyFEKK8/MOerpo+4yE2VGevuYTy1tX5cqdlZ8g6KppJYu7TazBdCS9ftaFsoursFPWoDHUyUfdE9mlrEkNdeY5R0zFuku2sEeSif+yJkZ0KDpTqPoQkchZLZGa2sImoy8ZxmXrFnS0PDtfGyvTlSmwKBgQCbJ0K405UTHZaTnGRNR1f0ZtQev8sKXWRUfqHXpRUc0V5YFcQrf9rGDehDvlZFA1rI8tGD7aSci2H/034GSCpSvwnJX1EdHuGdNTbXg46MVRTeK5QeF+bZdjFwF9XF5JYWySUqzsUymCLEjeiJPj79t2zcpLYVzKczq6Ink0zl9QKBgQCHQQQVvI1jFnojjDOf8nuBGfMMHc0mSRb5k2Ufe+giHzsalw9iQXBINoTOg8i5IQcC9xlBlxqvsDnMj6aDmQ9isXmqfE20w+hMjp2NnIKxtsG15wvwUYNX0bYfKzSZMp+28OxaOXtnz3f7Cb/87mqn0VC4awvIUe/HcgpyxL7YQQKBgCnO/o/HlBg+lODZ971LSEw3mVlf3VrBp9OM/BecCIBnesDJvL7sCQvtm3UFyUF3kfMsW4DtfLoiZRoG56LRt3XsvLpi0PzD4Y/3UvvoG3V9R85Gd/dUAYT/8HqAMb7NxhQFx9otNb5YeKX2h37UIy1DTDZ4vAhLyZKG4X9AliwxAoGALEmViVGBUPkSIPBVEobdRezU9zlG36GSbMymerpbgwluTAHQKc2U/wCMxABwdDZHIbZNQxMe4cX6PlyULALy9Wb+tzWDnz3ZmOcJILQsWwWQM7q5G3IY5O54TZ7ni+t0A5egNjUAQCMSr9+F7i6G7v6slNXCOVbih1yozUcMiEg=",
                        "json",
                        "GBK",
                        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxMgLRxy9650Bwy+/HSVofDQb0JCZuv0a+/u8SIOiOlCsjEodi9mmVQyUgQ7Kt8PdL0AWKOlM7eif3sBtJqw3pV/KYkQhN6W0seuFQeGRGUlwyoNhCAXxsrTxPLN1CrXiUTGel+tgUk1Nk12kAvn7Wj9jGHwjA9LxdLAlj5VAUpEuy+bhtHGPo+gDXv4GolQXi5WxJEX2uCQEYKGRv4wgUJxSz00QRUUws7sH9idsA83YP5fwkqEBe8TPq7rpagYviLQeWeykXWuhVuGOnlHEBNc3ySGf7kXjk5q4M3qVhUSlD2cD8AOnjcnEx3jrsChhhtsdmWzCXPXUL1OEcmUSVQIDAQAB",
                        "RSA2");
        return alipayClient;
    }

}
