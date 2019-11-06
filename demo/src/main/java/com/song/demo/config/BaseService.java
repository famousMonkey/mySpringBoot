package com.song.demo.config;

import com.song.demo.entity.BaseEntity;

import java.util.Date;

/**
 * @ClassName: BaseService
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/11/6 14:32
 * @Version: 1.0
 **/

public class BaseService<T> {


    //保存前插入公共属性
    public void packageInsertProperty(BaseEntity entity){
        entity.setCreateTime(new Date());
        entity.setLastModifiedTime(new Date());
    }

    //更新前修改公共属性
    public void packageUpdateProperty(BaseEntity entity){
        entity.setLastModifiedTime(new Date());
    }

}
