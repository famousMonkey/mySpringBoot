package com.song.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.song.demo.util.DateSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

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
public class Student extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "学生姓名")
    @Column(name = "STUDENT_NAME", length = 10)
    private String name;

    @ApiModelProperty(value = "学生年龄")
    @Column(name = "STUDENT_AGE", length = 5)
    private Integer age;

    @ApiModelProperty(value = "出生年月")
    @JSONField(serializeUsing = DateSerializer.class)
    @Column(name = "STUDENT_BIRTHDAY", nullable = false, length = 11)
    private Date birthday;


}
