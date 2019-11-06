package com.song.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * @ClassName: School
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/11/6 14:00
 * @Version: 1.0
 **/
@Data
@Entity
@Table
public class School extends BaseEntity {

    @Column
    @NotBlank(message = "校园名称不能为空")
    private String name;

    @Lob
    @Column(columnDefinition="text")
    private String address;

}
