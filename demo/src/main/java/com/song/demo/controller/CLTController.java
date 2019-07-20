package com.song.demo.controller;

import com.song.demo.Service.CLTService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: CLTController
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/19 11:38
 * @Version: 1.0
 **/
@Controller
@RequestMapping(value = "clt")
@Api(tags = "车联体")
@Slf4j
public class CLTController {

    @Autowired
    private CLTService cltService;


    @GetMapping("realLogin/{phone}/{pwd}")
    public String realLogin(@PathVariable("phone")String phone,@PathVariable("pwd")String pwd){
        return cltService.realLogin(phone,pwd);
    }

}
