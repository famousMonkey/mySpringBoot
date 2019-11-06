package com.song.demo.config;

import com.song.demo.Service.StudentService;
import com.song.demo.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: MyTask
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/8/8 10:53
 * @Version: 1.0
 **/
@Component
@EnableScheduling
@Slf4j
public class MyTask{

    @Autowired
    private StudentService studentService;

    @Scheduled(cron = "* * 22 * * ?")
    public void myTask(){
        log.info("------start task-----");
        List<Student> all = studentService.findAll(0);
        for (Student student : all) {
            if(student.getName().equals("吴博涛")){
                Integer age = student.getAge();
                if(age==null){
                    age=0;
                }else{
                    age+=1;
                }
                student.setAge(age);
                studentService.updateResource(student.getId(),student);
                log.info("更新学生信息ok");
            }
        }
        log.info("------end task-----");
    }

}
