package com.song.demo.Service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.song.demo.Service.AliPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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

            int i=1;
            int j=1;
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
                        Boolean cancelMark = cancel(orderId);
                        if(cancelMark){
                            log.info("撤销成功");
                            return true;
                        }else{
                            log.info("撤销失败");
                            return false;
                        }
                    }
                }else{
                    try {
                        TimeUnit.SECONDS.sleep(5*j);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ++j;
                    if(j==6){
                        log.info("第"+j+"次等待预支付结果");
                        Boolean cancelMark = cancel(orderId);
                        if(cancelMark){
                            log.info("撤销成功");
                            return true;
                        }else{
                            log.info("撤销失败");
                            return false;
                        }
                    }else{
                        log.info("第"+j+"次等待预支付结果");
                    }
                }
            }
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
                        Boolean cancelMark = cancel(orderId);
                        if(cancelMark){
                            log.info("撤销成功");
                            return true;
                        }else{
                            log.info("撤销失败");
                            return false;
                        }
                    }
                }else{
                    return false;
                }
            }
        }


    }

    /**
     * @Author 宋正健
     * @Description //TODO(支付宝转账)
     * @Date 2019/6/11 14:18
     * @Param [orderId, amount]
     * @Return java.lang.Boolean
     */
    @Override
    public Boolean transfer(String orderId, String amount,String name) {
        AlipayClient aliPayClient = createCommonParam();
        AlipayFundTransToaccountTransferRequest request = createTransferParam(orderId, amount,name);
        AlipayFundTransToaccountTransferResponse response = null;
        try {
            response  = aliPayClient.execute(request);
            log.info("支付宝转账返回值："+response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @Author 宋正健
     * @Description //TODO(支付宝订单退款)
     * @Date 2019/6/12 14:47
     * @Param [orderId, amount]
     * @Return java.lang.Boolean
     */
    @Override
    public Boolean refund(String orderId, String amount) {
        AlipayClient alipayClient = createCommonParam();
        AlipayTradeRefundRequest request = createRefundParam(orderId, amount);
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
            log.info("支付宝退款结果："+response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @Author 宋正健
     * @Description //TODO(手机网站支付)
     * @Date 2019/6/15 16:50
     * @Param [orderId]
     * @Return java.lang.String
     */
    @Override
    public String wapPay(String orderId){
        AlipayClient alipayClient = createCommonParam();
        AlipayTradeWapPayRequest request = createWapPayParam(orderId);
        String form=null;
        try {
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
            form=response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }


    /**
     * @Author 宋正健
     * @Description //TODO(支付宝订单撤销)
     * @Date 2019/6/12 16:44
     * @Param [orderId]
     * @Return java.lang.Boolean
     */
    private Boolean cancel(String orderId){
        AlipayClient alipayClient = createCommonParam();
        AlipayTradeCancelRequest request = createCancelParam(orderId);
        AlipayTradeCancelResponse response = null;
        try {
            response = alipayClient.execute(request);
            log.info("撤销订单返回结果："+response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 创建撤销参数
     * @param orderId
     * @return
     */
    private AlipayTradeCancelRequest createCancelParam(String orderId){
        Map<String,String> param=new HashMap<>();
        param.put("out_trade_no",orderId);
        String s = JSON.toJSONString(param);
        AlipayTradeCancelRequest request=new AlipayTradeCancelRequest();
        request.setBizContent(s);
        return request;
    }


    /**
     * 创建退款参数
     * @param orderId
     * @param amount
     * @return
     */
    private AlipayTradeRefundRequest createRefundParam(String orderId, String amount){
        Map<String,String> param=new HashMap<>();
        param.put("out_trade_no",orderId);
        param.put("refund_amount",amount);
        String s1 = UUID.randomUUID().toString();
        log.info("UUID==>"+s1);
        param.put("out_request_no",s1);//同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
        String s = JSON.toJSONString(param);
        AlipayTradeRefundRequest request=new AlipayTradeRefundRequest();
        request.setBizContent(s);
        return request;
    }


    /**
     * 查询结果返回标识
     * @param orderId
     * @return
     */
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
     * 创建转账参数
     * @param orderId
     * @param amount
     * @return
     */
    private AlipayFundTransToaccountTransferRequest createTransferParam(String orderId,String amount,String name){
        Map<String,String> param=new HashMap<>();
        param.put("out_biz_no",orderId);
        param.put("amount",amount);
        param.put("payee_type","ALIPAY_LOGONID");//ALIPAY_USERID  //收款方账户类型
        param.put("payee_account","ifklpk3964@sandbox.com");//收款方账户 与payee_type配合使用
        param.put("payer_show_name",name);//付款方姓名
        //param.put("payee_real_name","沙箱环境");//收款方真实姓名

        String s = JSON.toJSONString(param);
        AlipayFundTransToaccountTransferRequest request=new AlipayFundTransToaccountTransferRequest();
        request.setBizContent(s);
        return request;
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
        param.put("notify_url","http://10.198.1.119:9001/alipay/callback/notify");
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
     * 创建手机网站支付参数
     * @param orderId
     * @return
     */
    private AlipayTradeWapPayRequest createWapPayParam(String orderId){
        Map<String,String> param=new HashMap<>();;
        param.put("subject","测试---");
        param.put("out_trade_no",orderId);
        param.put("total_amount","1");//测试 1元
        param.put("product_code","QUICK_WAP_WAY");
        AlipayTradeWapPayRequest request=new AlipayTradeWapPayRequest();
        request.setReturnUrl("http://10.198.1.119:9001/alipay/returnurl");//回调地址
        request.setNotifyUrl("http://10.198.1.119:9001/alipay/callback/notice");//异步通知地址
        String s = JSON.toJSONString(param);
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
                        "utf-8",
                        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxMgLRxy9650Bwy+/HSVofDQb0JCZuv0a+/u8SIOiOlCsjEodi9mmVQyUgQ7Kt8PdL0AWKOlM7eif3sBtJqw3pV/KYkQhN6W0seuFQeGRGUlwyoNhCAXxsrTxPLN1CrXiUTGel+tgUk1Nk12kAvn7Wj9jGHwjA9LxdLAlj5VAUpEuy+bhtHGPo+gDXv4GolQXi5WxJEX2uCQEYKGRv4wgUJxSz00QRUUws7sH9idsA83YP5fwkqEBe8TPq7rpagYviLQeWeykXWuhVuGOnlHEBNc3ySGf7kXjk5q4M3qVhUSlD2cD8AOnjcnEx3jrsChhhtsdmWzCXPXUL1OEcmUSVQIDAQAB",
                        "RSA2");
        return alipayClient;
    }

}
