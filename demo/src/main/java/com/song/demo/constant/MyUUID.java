package com.song.demo.constant;

import com.song.demo.util.UUIDUtil;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * @ClassName: MyUUID
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/5 15:48
 * @Version: 1.0
 **/

public class MyUUID implements IdentifierGenerator {


    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return UUIDUtil.compressedUUID();
    }
}
