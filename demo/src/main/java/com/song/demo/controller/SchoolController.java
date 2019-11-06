package com.song.demo.controller;

import com.song.demo.Service.SchoolService;
import com.song.demo.config.ResultMsg;
import com.song.demo.entity.School;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @ClassName: SchoolController
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/11/6 14:08
 * @Version: 1.0
 **/
@Api(tags = "操作学校")
@RestController
@RequestMapping(value = "/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;


    @ApiOperation(value = "保存信息")
    @PostMapping(value = "/save")
    public ResultMsg save(@RequestBody @Valid School school, BindingResult result){
        if(result.hasErrors()){
            return new ResultMsg(10022,result.getFieldError().getDefaultMessage());
        }
        School entity = schoolService.saveEntity(school);
        if(entity!=null){
            return new ResultMsg(10000,"save Success",entity);
        }else{
            return new ResultMsg(10022,"save error");
        }
    }



}
