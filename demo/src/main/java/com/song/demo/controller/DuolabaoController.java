package com.song.demo.controller;

import com.alibaba.fastjson.JSON;
import com.song.demo.Service.DuolabaoService;
import com.song.demo.constant.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: DuolabaoController
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/6/27 14:39
 * @Version: 1.0
 **/
@RestController
@RequestMapping(value = "duolaba")
@Api(tags = "哆啦宝支付")
@Slf4j
public class DuolabaoController {

    @Autowired
    private DuolabaoService duolabaoService;

    @GetMapping(value = "/payCreate")
    @ApiOperation(value = "哆啦宝创建支付订单",notes = "哆啦宝")
    public String payCreate(){
        Result result = duolabaoService.payCreate();
        log.info("controller=>>"+result.toString());
        return JSON.toJSONString(result.getMessage());

    }

}
