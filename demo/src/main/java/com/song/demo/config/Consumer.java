package com.song.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName: Consumer
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/6/26 15:17
 * @Version: 1.0
 **/
@Component
@Slf4j

public class Consumer {

    @RabbitListener(queues = "famous")
    @RabbitHandler
    public void process(String message) {
        log.info("111接收的消息为: {}", message);
    }

    @RabbitListener(queues = "monkey")
    @RabbitHandler
    public void process1 (String message) {
        log.info("222接收的消息为: {}", message);
    }

}
