package com.song.demo.Service;

import com.song.demo.entity.Student;

import java.util.List;

public interface StudentService {

    Student saveResource(Student student);
    boolean deleteResource(String id);
    boolean updateResource(String id,Student student);
    Student findById(String id);
    List<Student> findAll();

}
