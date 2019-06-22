package com.song.demo.controller;

import com.song.demo.Service.AliasesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: AliasesController
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/6/22 15:23
 * @Version: 1.0
 **/
@Api(tags = "阿里云消息推送")
@Controller
public class AliasesController {

    @Autowired
    private AliasesService aliasesService;

    @ResponseBody
    @ApiOperation(value = "查询", notes = "设备I单查询")
    @GetMapping(value = "/query")
    public void queryPushAccount(){
        try {
            aliasesService.query();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @ApiOperation(value = "查询list")
    @GetMapping(value = "/queryList")
    public void querylist(){
        try {
            aliasesService.queryRecords();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
