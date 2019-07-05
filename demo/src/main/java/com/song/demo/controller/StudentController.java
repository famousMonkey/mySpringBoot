package com.song.demo.controller;

import com.song.demo.Service.StudentService;
import com.song.demo.entity.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: StudentController
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/5 15:28
 * @Version: 1.0
 **/
@Api(tags = "操作学生类")
@RestController
@RequestMapping(value = "student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "保存信息",notes ="保存")
    @PostMapping(value = "/save")
    public String save(@RequestBody Student student){
        Student student1 = studentService.saveResource(student);
        if(student1!=null){
            return "success";
        }else{
            return "fail";
        }
    }

    @ApiOperation(value = "删除信息",notes ="删除")
    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id") String id){
        boolean b = studentService.deleteResource(id);
        if(b){
            return "success";
        }else{
            return "fail";
        }
    }

    @ApiOperation(value = "更新信息",notes = "更新")
    @PutMapping(value = "/update/{id}")
    public String update(@PathVariable(value = "id")String id,@RequestBody Student student){
        boolean b = studentService.updateResource(id, student);
        if(b){
            return "success";
        }else{
            return "fail";
        }
    }


    @ApiOperation(value = "根据id查询",notes = "查询单个")
    @GetMapping(value = "/findById/{id}")
    public Student findById(@PathVariable("id")String id){
       return studentService.findById(id);
    }


    @ApiOperation(value = "查询所有",notes = "查全部")
    @GetMapping(value = "/findAll")
    public List<Student> findAll(){
        return studentService.findAll();
    }



}
