package com.song.demo.repository;


import com.song.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student,String>, JpaRepository<Student,
        String>, JpaSpecificationExecutor<Student> {

    Set<Student> findByAgeAndBirthdayAfter(Integer age, Date now);

    @Query(value = "select * from student where date_sub(curdate(),interval ?1 day )<=date(student_birthday)",nativeQuery = true)
    List<Student> queryByDays(String days);

    Student findByNameAndAgeNot(String name,Integer age);
}
