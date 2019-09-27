package com.song.demo.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @ClassName: Man
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/8/26 16:40
 * @Version: 1.0
 **/
@Data
public class Man implements Serializable {


    private Integer id;


    @NotEmpty(message = "名字为null")
    private String name;

}
