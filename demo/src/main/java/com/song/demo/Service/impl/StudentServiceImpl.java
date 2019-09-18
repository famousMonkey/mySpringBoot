package com.song.demo.Service.impl;

import com.song.demo.Service.StudentService;
import com.song.demo.entity.Student;
import com.song.demo.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
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
    public Student saveResource(Student student) {
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
        Optional<Student> resource = studentRepository.findById(id);
        student.setId(id);
        Student stu = resource.get();
        try {
            PropertyUtils.copyProperties(stu,student);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
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
        Student student = stu.orElse(new Student("err","当你看到这个结果时，说明没有查到结果",000,new Date()));
        return student;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
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
}
