package com.song.demo.controller;

import com.song.demo.Service.PoliceService;
import com.song.demo.config.ResultMsg;
import com.song.demo.entity.Police;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName: PoliceController
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/11/12 10:23
 * @Version: 1.0
 **/
@Api(tags = "jpa练习")
@RestController
@RequestMapping(value = "/police")
public class PoliceController {

    @Resource
    private PoliceService policeService;

    @ApiOperation(value = "持久化数据")
    @PostMapping(value = "/save")
    public ResultMsg save(@RequestBody Police police){
        Police persistence = policeService.persistence(police);
        return new ResultMsg(10000,"persistence success",persistence);
    }


    @ApiOperation(value = "删除数据")
    @DeleteMapping(value = "/delete/{id}")
    public ResultMsg delete(@PathVariable("id")String id){
        policeService.delete(id);
        return new ResultMsg(10000,"delete success");
    }


    @ApiOperation(value = "更新数据")
    @PutMapping(value = "/update/{id}")
    public ResultMsg update(@PathVariable("id")String id,@RequestBody Police police){
        police.setId(id);
        Police update = policeService.update(police);
        return new ResultMsg(10000,"update success",update);
    }


    @ApiOperation(value = "查询数据")
    @GetMapping(value = "/select/{number}")
    public ResultMsg query(@PathVariable("number")String number){
        Police entity = policeService.query(number);
        return new ResultMsg(10000,"query success",entity);
    }


}
