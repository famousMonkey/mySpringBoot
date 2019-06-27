package com.song.demo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




/**
 * @ClassName: RabbitMQConfig
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/6/26 15:07
 * @Version: 1.0
 **/
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue monkeyQueeu(){
        Queue monkey = new Queue("monkey");
        return monkey;
    }

    @Bean
    public Queue famousQueue(){
        return new Queue("famous");
    }

}
