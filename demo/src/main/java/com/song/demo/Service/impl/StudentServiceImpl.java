package com.song.demo.Service.impl;

import com.song.demo.Service.StudentService;
import com.song.demo.entity.BaseEntity;
import com.song.demo.entity.Student;
import com.song.demo.repository.StudentRepository;
import com.song.demo.util.CopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName: StudentServiceImpl
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/5 15:25
 * @Version: 1.0
 **/
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public boolean saveAll(List<Student> list) {
        for (Student student : list) {
            this.packageInsertProperty(student);
        }
        List<Student> students = studentRepository.saveAll(list);
        log.info("批量保存：{}",students);
        return true;
    }

    @Override
    public Student saveResource(Student student) {
        this.packageInsertProperty(student);
        Student resource = studentRepository.save(student);
        return resource;
    }

    @Override
    public boolean deleteResource(String id) {
        studentRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean updateResource(String id, Student student) {
        this.packageUpdateProperty(student);
        Optional<Student> resource = studentRepository.findById(id);
        student.setId(id);
        Student stu = resource.get();
        CopyUtil.copyNonNullProperties(student,stu);
        Student save = studentRepository.save(stu);
        if(save!=null){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public Student findById(String id) {
        Optional<Student> stu = studentRepository.findById(id);
        Student student = stu.orElse(new Student("为查询带结果",404,new Date()));
        return student;
    }

    @Override
    public List<Student> findAll(Integer mark) {
        if(mark!=null&&mark!=0){
            return this.findByDay(mark);
        }else{
            return studentRepository.findAll();
        }
    }

    @Override
    public Set<Student> findByAgeAndBirthdayAfter(Integer age, Date now) {
        return studentRepository.findByAgeAndBirthdayAfter(age,now);
    }

    @Override
    public Set<Student> findByDate(Date date) {
        List<Student> all = studentRepository.findAll();
        Set<Student> allStudent=new HashSet<>();
        for (Student student : all) {
            if(student.getBirthday().before(date)){
                allStudent.add(student);
            }else{
                continue;
            }
        }
        log.info("按生日查：{}",allStudent);
        return allStudent;

    }


    @Override
    public List<Student> findByDay(Integer days) {
        return studentRepository.queryByDays(days.toString());
    }

    @Override
    public Student findByNameAndAgeNot(String name,Integer age){
        return studentRepository.findByNameAndAgeNot(name,age);
    }


    //保存前插入公共属性
    private void packageInsertProperty(BaseEntity entity){
        entity.setCreateTime(new Date());
        entity.setLastModifiedTime(new Date());
    }

    //更新前修改公共属性
    private void packageUpdateProperty(BaseEntity entity){
        entity.setLastModifiedTime(new Date());
    }

}
