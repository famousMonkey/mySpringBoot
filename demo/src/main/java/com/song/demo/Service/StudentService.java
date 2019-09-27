package com.song.demo.Service;

import com.song.demo.entity.Student;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface StudentService {

    boolean saveAll(List<Student> list);
    Student saveResource(Student student);
    boolean deleteResource(String id);
    boolean updateResource(String id,Student student);
    Student findById(String id);
    List<Student> findAll(Integer mark);
    Set<Student> findByAgeAndBirthdayAfter(Integer age, Date now);
    Set<Student> findByDate(Date date);
    List<Student> findByDay(Integer days);

}
