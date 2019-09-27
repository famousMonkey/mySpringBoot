package com.song.demo.util;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

public class DateSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (null != object) {
            serializer.write(DateUtil.getDateTime((Date) object));
        } else {
            serializer.write("");
        }
    }
}