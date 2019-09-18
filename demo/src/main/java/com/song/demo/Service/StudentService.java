package com.song.demo.Service;

import com.song.demo.entity.Student;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface StudentService {

    Student saveResource(Student student);
    boolean deleteResource(String id);
    boolean updateResource(String id,Student student);
    Student findById(String id);
    List<Student> findAll();
    Set<Student> findByAgeAndBirthdayAfter(Integer age, Date now);
    Set<Student> findByDate(Date date);

}
