package com.song.demo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @ClassName: Police
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/11/12 9:37
 * @Version: 1.0
 **/
@Table(name = "JingCha",indexes = {@Index(name = "POLICE_NUMBER",columnList = "number")},uniqueConstraints = {@UniqueConstraint(columnNames = {"number","name"})})
@Entity
@Data
public class Police extends BaseEntity {

    @ApiModelProperty(value = "编号")
    private String number;
    @ApiModelProperty(value = "名字")
    private String name;
    @ApiModelProperty(value = "性别")
    private Integer sex;

}
