package com.song.demo.controller;

import com.song.demo.Service.SchoolService;
import com.song.demo.config.ResultMsg;
import com.song.demo.entity.School;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        School entity = schoolService.save(school);
        if(entity!=null){
            return new ResultMsg(10000,"save Success",entity);
        }else{
            return new ResultMsg(10022,"save error");
        }
    }



    @ApiOperation(value = "更新信息")
    @PutMapping(value = "/update/{id}")
    public ResultMsg update(@PathVariable("id")String id, @RequestBody @Valid School school, BindingResult result){
        if(result.hasErrors()){
            return new ResultMsg(10022,result.getFieldError().getDefaultMessage());
        }
        if(StringUtils.isBlank(id)){
            return new ResultMsg(10022,"required value is null");
        }
        school.setId(id);
        Boolean flag = schoolService.update(school);
        if(flag){
            return new ResultMsg(10000,"update Success");
        }else{
            return new ResultMsg(10022,"update fail");
        }
    }


    @ApiOperation(value = "删除信息")
    @DeleteMapping(value = "/delete/{id}")
    public ResultMsg delete(@PathVariable("id")String id){
        if(StringUtils.isNotBlank(id)){
            Boolean flag = schoolService.delete(id);
            if(flag){
                return new ResultMsg(10000,"delete Success");
            }else{
                return new ResultMsg(10022,"delete fail");
            }
        }else{
            return new ResultMsg(10022,"required value is null");
        }
    }


    @ApiOperation(value = "删除信息")
    @GetMapping(value = "/query/{id}")
    public ResultMsg query(@PathVariable("id") String id){
        if(StringUtils.isNotBlank(id)){
            School entity = schoolService.query(id);
            return new ResultMsg(10000,"query Success",entity);
        }else{
            return new ResultMsg(10022,"required value is null");
        }
    }


}
