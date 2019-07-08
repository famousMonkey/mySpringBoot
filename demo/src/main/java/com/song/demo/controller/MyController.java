package com.song.demo.controller;

import com.song.demo.Service.MyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: MyController
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/1 10:25
 * @Version: 1.0
 **/
@Api(tags = "测试注解")
@RestController
@RequestMapping(value = "my")
public class MyController {

    @Autowired
    private MyService myService;


    @ApiOperation(value = "@Primary练习",notes = "@Primary")
    @GetMapping(value = "/introduce/{name}")
    public void my(@PathVariable("name")String name){
        System.out.println("名字："+name);
        myService.sayHi(name);
    }
    @ApiOperation(value = "@Async练习",notes = "异步调用注解")
    @GetMapping(value = "/{param}")
    public String introduce(@PathVariable(value = "param")String param){
        System.out.println("==========> start");
        myService.introduce(param);
        long time = System.currentTimeMillis();
        System.out.println("==========> end "+time);
        return "amazing";
    }


}
