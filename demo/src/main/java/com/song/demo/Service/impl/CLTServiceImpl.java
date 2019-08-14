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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final String myUrl="http://frgclt.xianjintong.com:30001";
    private static final String returnMsg="会话过期，请重新登陆！";


    /**
     * @Author 宋正健
     * @Description //TODO(登陆)
     * @Date 2019/7/26 14:04
     * @Param [phone, pwd]
     * @Return java.util.Map
     */
    @Override
    public Map realLogin(String phone, String pwd) {
        String loginParam = createLoginParam(phone, pwd);
        String url=myUrl+"/api/account/realLogin";
        Map map = sendRequest(url, loginParam);
        if("0".equalsIgnoreCase((String) map.get("code"))){
            saveCcSessionId(map);//缓存
        }
        return map;
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
     * @Description //TODO(将ccsessionId存入redis)
     * @Date 2019/8/8 10:22
     * @Param [myData]
     * @Return void
     */
    private void saveCcSessionId(Map myData){
        Map<String,Object> data = (Map)myData.get("data");
        log.info("登陆获取的数据：\n{}",data);
        String ccsessionId = (String)data.get("ccsessionId");
        log.info("ccsessionId数据：\n{}",ccsessionId);
        //获取用户信息
        Map<String,String> userResult = (Map)data.get("userResult");
        String phone = userResult.get("phone");
        log.info("phone数据：\n{}",phone);
        if(ccsessionId!=null&&phone!=null){
            log.info("\n-----------存入redis----------");
            stringRedisTemplate.opsForValue().set("clt.phoneNumber:"+phone,ccsessionId,30L, TimeUnit.MINUTES);
        }
    }

    /**
     * @Author 宋正健
     * @Description //TODO(获取redis中的value)
     * @Date 2019/8/8 14:24
     * @Param [phone]
     * @Return java.lang.String
     */
    private String getCcSessionId(String phone){
        if(stringRedisTemplate.hasKey("clt.phoneNumber:"+phone)){
            log.info("\n-----------获取redis----------");
            stringRedisTemplate.expire("clt.phoneNumber:"+phone,30L,TimeUnit.MINUTES);
            return stringRedisTemplate.opsForValue().get("clt.phoneNumber:" + phone);
        }else{
            return null;
        }
    }

    /**
     * @Author 宋正健
     * @Description //TODO(心跳)
     * @Date 2019/8/8 10:56
     * @Param [ccSessionId]
     * @Return java.util.Map
     */
    @Override
    public Map alive(String phone) {
        String ccSessionId = getCcSessionId(phone);
        if(ccSessionId!=null){
            String url=myUrl+"/api/common/alive?CCSessionId="+ccSessionId;
            return heartBeatRequest(url);
        }else{
            return errorResult();
        }
    }

    /**
     * @Author 宋正健
     * @Description //TODO(商家预下单)
     * @Date 2019/7/26 14:26
     * @Param [totalAmount]
     * @Return java.util.Map
     */
    @Override
    public Map prepay(String totalAmount,String phone,String qrCode) {
        String ccSessionId = getCcSessionId(phone);
        String outTradeNo="CLT"+new Faker(Locale.CHINA).code().asin();
        if(ccSessionId!=null){
            String prepayParam = createPrepayParam(outTradeNo,totalAmount,ccSessionId,qrCode);
            String url=myUrl+"/modules/store/prepay";
            Map map = sendRequest(url, prepayParam);
            return handlePrepayResult(outTradeNo,phone,map,ccSessionId);
        }else{
            return errorResult();
        }
    }

    /**
     * @Author 宋正健
     * @Description //TODO(预下单参数)
     * @Date 2019/8/14 14:40
     * @Param [outTradeNo, totalAmount, ccSessionId, qrCode]
     * @Return java.util.Map
     */
    private String createPrepayParam(String outTradeNo,String totalAmount,String ccSessionId,String qrCode){
        Faker faker=new Faker(Locale.CHINA);
        Map<String,String> request=new HashMap<>();
        request.put("appID","2019080609");
        request.put("channelNo","XHTJYZ");
        request.put("industryCode","5541");
        request.put("subject","123456");
        String body = faker.university().name();
        request.put("body",body);
        request.put("totalAmount",totalAmount);
        request.put("productCode","");
        request.put("ccSessionId",ccSessionId);
        request.put("autoOut","0");
        request.put("tradeType","4");
        request.put("qrCode",qrCode);
        request.put("returnUrl","");
        request.put("notifyUrl","http://adsgodlove.vicp.cc:44674/payservice/cltNotify");
        request.put("companyCode","91321091MA1NP6RR5W");
        request.put("outTradeNo",outTradeNo);
        request.put("signature","");//将signature设置为空字符串，然后将整个json字符串的md5值用私钥加密
        String s = haveSignature(request);
        return s;
    }


    /**
     * @Author 宋正健
     * @Description //TODO(订单的支付结果)
     * @Date 2019/8/14 14:41
     * @Param [outTradeNo, phone, myData, ccSessionId]
     * @Return java.util.Map
     */
    private Map handlePrepayResult(String outTradeNo,String phone,Map myData,String ccSessionId){
        Map<String,String> map = handlePrepayResultData(myData, ccSessionId);
        if("0".equalsIgnoreCase(map.get("code"))){
            while(true){
                Map data = jmccPayStatus(outTradeNo, phone);
                if("0".equalsIgnoreCase((String)data.get("code"))){
                    String str = (String)data.get("data");
                    Map<String,String> mapStr = JSON.parseObject(str,Map.class);
                    log.info("下单后的支付结果：{}",mapStr);
                    if("SUCCESS".equalsIgnoreCase(mapStr.get("code"))||"ERROR".equalsIgnoreCase(mapStr.get("code"))){
                        return mapStr;
                    }else{
                        continue;
                    }
                }else{
                    return data;
                }
            }
        }else{
            return map;
        }
    }

    /**
     * @Author 宋正健
     * @Description //TODO(处理预下单返回结果)
     * @Date 2019/8/8 15:23
     * @Param [myData, ccSessionId]
     * @Return java.util.Map
     */
    private Map handlePrepayResultData(Map myData,String ccSessionId){
        Map<String,Object> data = (Map)myData.get("data");
        if(data!=null){
            log.info("预下单。。。");
            log.info("data:\n{}",data);
            String payUrl = (String)data.get("payUrl");
            String url = payUrl + "&ccSessionId=" + ccSessionId;
            log.info("payUrl:\n{}",url);
            data.put("payUrl",url);
            return data;
        }else{
            log.info("下单。。。");
            return myData;
        }
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
     * @Return java.util.Map
     */
    @Override
    public Map jmccPayStatus(String outTradeNo,String phone) {
        String ccSessionId = getCcSessionId(phone);
        if(ccSessionId!=null){
            String jmccPayStatusParam = createJmccPayStatus(outTradeNo,ccSessionId);
            String url=myUrl+"/modules/store/jmccPayStatus";
            return sendRequest(url, jmccPayStatusParam);
        }else{
            return errorResult();
        }
    }

    private String createJmccPayStatus(String outTradeNo,String ccSessionId){
        Map<String,String> request = new HashMap<>();
        request.put("channelNo","XHTJYZ");
        request.put("outTradeNo",outTradeNo );
        request.put("ccSessionId",ccSessionId);
        return JSON.toJSONString(request);
    }


    /**
     * @Author 宋正健
     * @Description //TODO(发送请求)
     * @Date 2019/7/26 16:42
     * @Param [url, param]
     * @Return java.util.Map
     */
    private Map sendRequest(String url,String param){
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


    /**
     * @Author 宋正健
     * @Description //TODO(心跳请求)
     * @Date 2019/8/8 14:20
     * @Param [url]
     * @Return java.util.Map
     */
    private Map heartBeatRequest(String url){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        log.info("请求地址：\n{}",url);
        Map map=null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String context = EntityUtils.toString(entity, "UTF-8");
            log.info("返回数据：\n{}",context);
            map = JSON.parseObject(context, Map.class);
            log.info("返回数据：\n{}.",map);
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

    /**
     * @Author 宋正健
     * @Description //TODO(会话过期结果)
     * @Date 2019/8/14 15:03
     * @Param []
     * @Return java.util.Map
     */
    private static Map errorResult(){
        Map map=new HashMap();
        map.put("code","10022");
        map.put("msg",returnMsg);
        return map;
    }

}
