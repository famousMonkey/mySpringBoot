package com.song.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.song.demo.util.DateSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @ClassName: BaseEntity
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/9/19 14:26
 * @Version: 1.0
 **/
@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    @GenericGenerator(name = "MyUUID",strategy = "com.song.demo.constant.MyUUID")
    @GeneratedValue(generator = "MyUUID")
    @ApiModelProperty(value = "主键id",hidden = true)
    @Column(name = "RES_ID", length = 24)
    private String id;

    @JSONField(serializeUsing = DateSerializer.class)
    @ApiModelProperty(value="插入时间",hidden = true)
    @Column(name = "CREATED_TIME", nullable = false, length = 11)
    private Date createTime;


    @JSONField(serializeUsing = DateSerializer.class)
    @ApiModelProperty(value="最后操作时间",hidden = true)
    @Column(name = "LAST_MODIFIED_TIME", nullable = false, length = 11)
    private Date lastModifiedTime;

}
