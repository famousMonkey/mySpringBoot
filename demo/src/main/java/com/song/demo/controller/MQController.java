package com.song.demo.controller;

import io.swagger.annotations.Api;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: MQController
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/6/26 15:10
 * @Version: 1.0
 **/
@RestController
@RequestMapping("rabbitmq")
@Api(tags = "消息队列接口")
public class MQController {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    @GetMapping("/send/{message}")
    public String send(@PathVariable("message") String message){
        rabbitmqTemplate.convertAndSend("monkey",message);
        rabbitmqTemplate.convertAndSend("famous","123"+message);
        return "消息："+message;
    }

}
