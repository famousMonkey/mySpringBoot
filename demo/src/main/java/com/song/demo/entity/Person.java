package com.song.demo.entity;

import lombok.Data;

import javax.validation.Valid;


/**
 * @ClassName: Person
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/8/26 16:44
 * @Version: 1.0
 **/

@Data
public class Person {

//    private String man_id;
//
//    @NotBlank(message = "man名字为null")
//    private String man_name;
//
//    private String woman_id;
//
//    @NotBlank(message = "woman名字为null")
//    private String woman_name;
    @Valid
    private Man man;

    @Valid
    private Woman woman;

}
