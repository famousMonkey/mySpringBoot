package com.song.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: Student
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/5 10:08
 * @Version: 1.0
 **/
@Data
public class Student implements Serializable {

    private String id;
    private String name;
    private Integer age;

}
