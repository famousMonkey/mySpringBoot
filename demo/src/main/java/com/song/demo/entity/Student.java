package com.song.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName: Student
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/5 10:08
 * @Version: 1.0
 **/
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "学生信息实体")
public class Student implements Serializable {

    @Id
    @GenericGenerator(name = "MyUUID",strategy = "com.song.demo.constant.MyUUID")
    @GeneratedValue(generator = "MyUUID")
    @ApiModelProperty(value = "学生id",hidden = true)
    private String id;
    @ApiModelProperty(value = "学生姓名")
    private String name;
    @ApiModelProperty(value = "学生年龄")
    private Integer age;

}
