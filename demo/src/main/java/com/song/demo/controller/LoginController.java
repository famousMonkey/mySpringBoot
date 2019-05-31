package com.song.demo.controller;

import com.alibaba.fastjson.JSON;
import com.song.demo.constant.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/tologin")
    public String login(){
        return "login";
    }

    @GetMapping(value = "/success")
    public String success(){
        return "success";
    }

    @ResponseBody
    @GetMapping(value = "/mylogin")
    public Result mylogin(@RequestParam(value = "name")String name){
        log.info("===="+name);
        Result result=new Result();
        if(StringUtils.isBlank(name)){
            result.setSuccess(false);
            result.setMessage("用户名为空");
            return result;
        }else{
            if("123".equals(name)){
                result.setSuccess(true);
                return result;
            }else{
                result.setSuccess(false);
                result.setMessage("用户名错误");
                return result;
            }
        }
    }

    @GetMapping(value = "/redis")
    public String myRedis(){
        String name="monkey";
        String s = JSON.toJSONString(name);
        stringRedisTemplate.opsForValue().set("wwwsssaaaddd",s,30, TimeUnit.SECONDS);
        return "success";
    }

    @GetMapping(value = "/getvalue")
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
