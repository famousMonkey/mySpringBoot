package com.song.demo.Service.impl;

import com.alibaba.fastjson.JSON;
import com.github.javafaker.Faker;
import com.song.demo.Service.CLTService;
import com.song.demo.config.CLTConfig;
import com.song.demo.util.MD5Util;
import com.song.demo.util.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
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

    @Autowired
    private CLTConfig cltConfig;

    private final String myUrl="http://frgclt.xianjintong.com:30001";
    //private final String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMIRxxLC5NZjovm8rd8JY5K8EIU/SQgkd+rg84HXEf78gKA8pa9fza7OWtuYRZz9car8TDUCgJNSPIZHuf5w1JIDoOH6qNZpbMJ2nM03SvyAY3Wt8D4+4wRfA3/CAHtpL5E2Z/KGSLhhPcnKwSFGqbrcuqRTRMWcPry8459gp5G9AgMBAAECgYBOrfpxpsY0DQ0kBtTPGfVephRkkTXAsVhUx4Kx30oSgLh9PllF8qdm+Y5ofSGr9izBT6xtyWfrUmnrXuUPPRtQqmaTayA+ihDcXPba+TlRl4H7VYpGJQVbWgCQ2cD7k+eG7LEZpea/JBb/34pJWdCJbr6uvh6jgY1Jw+g7MlXvWQJBAOpHNIVezTX7wjsfSVTroLtj/3PDDYGZBfFmFGyo7CbdkBaorROzPdJWbyBL6WAaL+FAWrp4GrDbbojDqc6NQfMCQQDUEC8gvICJQX8gRP9OkeU7kcULqbIpnBRwxqYGuP6SjKJy+TF0gdA+Qg7GpeyJBqNJ3chdkeXw8wHiK5F/NRmPAkBFXUfDpUoFDcF4V88SgaFZWkYsNDgvgusrihnKAknJSBh9XPvBtXQ8brMAUPmMJrS+cJtsUls0ugOEIsqICXLZAkB4wl6T/cQUZT/HWIMqctpe+buD0LasCz14myagXWhae8tmPZF0DxhO278eUA5KWYVS4wDeh96xPzCrYrQQBGTxAkEA3dIZc6W/RxIr/06BT1mAkoJBA83TGYHv0LWOdf5WEi7YVKI/7qNEXeFAUhXzgtD/UIvAdNNRcmeSUXkyuAqw8w==";



    /**
     * @Author 宋正健
     * @Description //TODO(登陆)
     * @Date 2019/7/26 14:04
     * @Param [phone, pwd]
     * @Return java.lang.String
     */
    @Override
    public String realLogin(String phone, String pwd) {
        String loginParam = createLoginParam(phone, pwd);
        String url=myUrl+"/api/account/realLogin";
        return sendRequest(url, loginParam);
    }

    private String createLoginParam(String phone, String pwd){
        Map<String,String> request=new HashMap<>();
        request.put("phone",phone);
        request.put("password",pwd);
        request.put("type","0");
        String param = JSON.toJSONString(request);
        return param;
    }

    /**
     * @Author 宋正健
     * @Description //TODO(商家预下单)
     * @Date 2019/7/26 14:26
     * @Param [totalAmount]
     * @Return java.lang.String
     */
    @Override
    public String prepay(String totalAmount) {
        String prepayParam = createPrepayParam(totalAmount);
        String url=myUrl+"/modules/store/prepay";
        return sendRequest(url, prepayParam);
    }

    private String createPrepayParam(String totalAmount){
        Faker faker=new Faker(Locale.CHINA);
        Map<String,String> request=new HashMap<>();
        request.put("appID","2019080609");
        request.put("channelNo","XHTJYZ");
        request.put("industryCode","5541");
        request.put("subject","123456");
        String body = faker.university().name();
        request.put("body",body);
        request.put("totalAmount",totalAmount);
        String productCode = faker.color().hex();
        request.put("productCode",productCode);
        request.put("ccSessionId","2@@EC233C3E819ED001CDC2D671E3808580");
        request.put("autoOut","0");
        request.put("tradeType","2");
        request.put("returnUrl","");
        request.put("notifyUrl","http://meatball.org.cn");
        request.put("companyCode","91321091MA1NP6RR5W");
        String outTradeNo = faker.phoneNumber().phoneNumber().replace("-","");
        request.put("outTradeNo","CLT"+outTradeNo);
        request.put("signature","");//将signature设置为空字符串，然后将整个json字符串的md5值用私钥加密
        String s = haveSignature(request);
        return s;
    }
    /**
     * @Author 宋正健
     * @Description //TODO(参数加密)
     * @Date 2019/8/7 17:01
     * @Param [request, param]
     * @Return java.lang.String
     */
    private String haveSignature(Map<String, String> request) {
        String param = JSON.toJSONString(request);
        String md5Value = null;
        try {
            md5Value = MD5Util.md5(URLEncoder.encode(param, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] sign = RSAUtil.encryptByPrivateKey(md5Value.getBytes(), cltConfig.getPrivateKey());
        String signature = URLEncoder.encode( new BASE64Encoder().encode(sign));
        request.put("signature",signature);
        return JSON.toJSONString(request);
    }


    /**
     * @Author 宋正健
     * @Description //TODO(获取订单支付状态)
     * @Date 2019/7/26 16:46
     * @Param [orderCode]
     * @Return java.lang.String
     */
    @Override
    public String jmccPayStatus(String outTradeNo ) {
        String jmccPayStatusParam = createJmccPayStatus(outTradeNo );
        String url=myUrl+"/modules/store/jmccPayStatus";
        return sendRequest(url, jmccPayStatusParam);
    }

    private String createJmccPayStatus(String outTradeNo ){
        Map<String,String> request = new HashMap<>();
        request.put("channelNo","XHTJYZ");
        request.put("outTradeNo",outTradeNo );
        request.put("ccSessionId","2@@EC233C3E819ED001CDC2D671E3808580");
        return JSON.toJSONString(request);
    }


    /**
     * @Author 宋正健
     * @Description //TODO(发送请求)
     * @Date 2019/7/26 16:42
     * @Param [url, param]
     * @Return java.lang.String
     */
    private String sendRequest(String url,String param){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        StringEntity body = new StringEntity(param, "utf-8");
        httpPost.setHeader("Content-Type","application/json");
        httpPost.setEntity(body);
        CloseableHttpResponse response = null;
        log.info("请求地址：\n{}",url);
        log.info("请求参数：\n{}",param);
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String context = EntityUtils.toString(entity, "UTF-8");
            log.info("返回数据：\n{}",context);
            return context;
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
        return null;
    }

}
