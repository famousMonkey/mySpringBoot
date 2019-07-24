package com.song.demo.controller;

import com.song.demo.Service.TLService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName: TLController
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/23 11:22
 * @Version: 1.0
 **/
@RestController
@RequestMapping(value = "TL")
@Slf4j
@Api(tags = "通联支付")
public class TLController {

    @Autowired
    private TLService tlService;

    @ApiOperation(value = "微信付款码换取openid",notes = "微信付款码换取openid")
    @GetMapping(value = "/exchange/{authCode}/{subAppId}")
    public Map<String,String> authCodeToUserId(@PathVariable("authCode")String authCode,@PathVariable("subAppId")String subAppId) throws Exception {
        return tlService.authcodeTouserid(authCode,subAppId);
    }



}
