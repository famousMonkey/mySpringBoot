package com.song.demo.Service.impl;

import com.song.demo.Service.SchoolService;
import com.song.demo.Service.StudentService;
import com.song.demo.config.BaseService;
import com.song.demo.entity.School;
import com.song.demo.entity.Student;
import com.song.demo.repository.SchoolRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Random;

/**
 * @ClassName: SchoolServiceImpl
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/11/6 14:06
 * @Version: 1.0
 **/
@Slf4j
@Service
public class SchoolServiceImpl extends BaseService<School> implements SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private StudentService studentService;

    @Transactional
    @Override
    public School saveEntity(School school) {
        super.packageInsertProperty(school);
        School result = schoolRepository.save(school);
        if(result!=null){
            log.info("保存的实体信息：{}",result);
            Student student=new Student("xixixix",29,new Date());
            Student stu = studentService.saveResource(student);
            int i = new Random().nextInt(10);
            log.info("i的值：{}",i);
            if(i%2==0){
                log.info("----手动回滚本地事务：{}",stu);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return result;
        }else{
            return null;
        }
    }
}
