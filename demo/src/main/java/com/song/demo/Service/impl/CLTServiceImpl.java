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
        String url="http://www.joinmore.com.cn/api/account/realLogin";
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
        String url="http://www.joinmore.com.cn/modules/store/prepay";
        return sendRequest(url, prepayParam);
    }

    private String createPrepayParam(String totalAmount){
        Map<String,String> request=new HashMap<>();
        request.put("appID","123456");
        request.put("channelNo","123456");
        request.put("industryCode","123456");
        request.put("subject","123456");
        request.put("body","");
        request.put("totalAmount",totalAmount);
        request.put("productCode","");
        request.put("ccSessionId","2@@3778101351E28CBAFF777BFD55EE7C5D");
        request.put("autoOut","0");
        request.put("tradeType","2");
        request.put("returnUrl","");
        request.put("notifyUrl","http://meatball.org.cn");
        request.put("companyCode","1234567890");
        request.put("outTradeNo","5770051");
        String param = JSON.toJSONString(request);
        return param;
    }


    /**
     * @Author 宋正健
     * @Description //TODO(获取订单支付状态)
     * @Date 2019/7/26 16:46
     * @Param [orderCode]
     * @Return java.lang.String
     */
    @Override
    public String jmccPayStatus(String orderCode) {
        String jmccPayStatusParam = createJmccPayStatus(orderCode);
        String url="http://www.joinmore.com.cn/modules/store/jmccPayStatus";
        return sendRequest(url, jmccPayStatusParam);
    }

    private String createJmccPayStatus(String orderCode){
        Map<String,String> request = new HashMap<>();
        request.put("orderCode",orderCode);
        request.put("ccSessionId","2@@3778101351E28CBAFF777BFD55EE7C5D");
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
        httpPost.setEntity(body);
        CloseableHttpResponse response = null;
        //Map myRes = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String context = EntityUtils.toString(entity, "UTF-8");
            log.info("返回数据：\n{}",context);
//            Map<String,String> data = (Map) JSON.parseObject(context).get("data");
//            log.info("获取JSON：\n{}",data);
//            myRes = JSON.parseObject(context,Map.class);
//            log.info("请求结果:\n{}",myRes);
//            return myRes;
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
