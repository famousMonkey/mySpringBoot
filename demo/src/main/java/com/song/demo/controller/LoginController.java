package com.song.demo.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
@RequestMapping(value = "login")
public class LoginController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ResponseBody
    @GetMapping(value = "/index")
    public String Login(HttpServletRequest request){
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        log.info("password:"+password);
        return "Hello "+name;
    }

    @GetMapping(value = "redis")
    public String myRedis(){
        String name="monkey";
        String s = JSON.toJSONString(name);
        stringRedisTemplate.opsForValue().set("wwwsssaaaddd",s,30, TimeUnit.SECONDS);
        return "success";
    }

    @GetMapping(value = "getvalue")
    public String getRedisKey(){
        log.info("我被调用了。。。");
        String value = stringRedisTemplate.opsForValue().get("wwwsssaaaddd");
        String s = JSON.parseObject(value, String.class);
        if(s!=null){
            return "index";
        }else{
            return "fail";
        }
    }



}
