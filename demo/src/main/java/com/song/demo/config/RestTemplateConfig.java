package com.song.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName: RestTemplateConfig
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/11/13 9:03
 * @Version: 1.0
 **/
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate registerTemplate() {
        RestTemplate restTemplate = new RestTemplate(getFactory());
        //这个地方需要配置消息转换器，不然收到消息后转换会出现异常
        //restTemplate.setMessageConverters(getConverts());
        return restTemplate;
    }


    /**
     * 功能描述： 初始化请求工厂
     *
     * @param
     * @author wangcanfeng
     * @time 2019/3/6 20:27
     * @since v1.0
     **/
    private SimpleClientHttpRequestFactory getFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(2000);
        factory.setReadTimeout(3000);
        return factory;
    }


}
