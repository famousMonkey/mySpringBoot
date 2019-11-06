package com.song.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: Teacher
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/8/5 15:00
 * @Version: 1.0
 **/
@Data
public class Teacher {

    private String id;
    private String name;
    @JSONField(name = "AGE")//注意观察生成的JSON串中age和其他字段的区别
    private Integer age;
    @NotNull(message = "address is not null ! ")
    private String address;

}
