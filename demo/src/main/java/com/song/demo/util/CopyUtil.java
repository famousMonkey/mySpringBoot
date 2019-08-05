package com.song.demo.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: CopyUtil
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/8/5 14:57
 * @Version: 1.0
 **/

public class CopyUtil {

    /**
     * @Author 宋正健
     * @Description //TODO(将空值的属性从目标实体类中复制到源实体类中)
     * @Date 2019/8/5 15:10
     * @Param [src 要将属性中的空值覆盖的对象(源实体类)
     * , target 从数据库根据id查询出来的目标对象]
     * @Return void
     */
    public static void copyNonNullProperties(Object  src,Object target){
        BeanUtils.copyProperties(src,target,getNullProperties(src));
    }

    /**
     * @Author 宋正健
     * @Description //TODO(将为空的properties给找出来,然后返回出来)
     * @Date 2019/8/5 15:10
     * @Param [src]
     * @Return java.lang.String[]
     */
    private static String[] getNullProperties(Object src) {
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> emptyName = new HashSet<>();
        for (PropertyDescriptor p : pds) {
            Object srcValue = srcBean.getPropertyValue(p.getName());
            if (srcValue == null) {
                emptyName.add(p.getName());
            }
        }
        String[] result = new String[emptyName.size()];
        return emptyName.toArray(result);
    }

}
