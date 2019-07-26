package com.song.demo.controller;

import com.song.demo.Service.CLTService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @ResponseBody
    @GetMapping("/realLogin/{phone}/{pwd}")
    @ApiOperation(value = "登陆",notes = "登陆")
    public String realLogin(@PathVariable("phone")String phone,@PathVariable("pwd")String pwd){
        return cltService.realLogin(phone,pwd);
    }

    @ResponseBody
    @GetMapping("/prepay/{totalAmount}")
    @ApiOperation(value = "商家预下单",notes = "预下单")
    public String prepay(@PathVariable("totalAmount")String totalAmount){
        return cltService.prepay(totalAmount);
    }

}
