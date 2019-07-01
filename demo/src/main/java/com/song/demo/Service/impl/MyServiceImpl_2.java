package com.song.demo.Service.impl;

import com.song.demo.Service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName: MyServiceImpl_2
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/1 10:22
 * @Version: 1.0
 **/
@Service
@Slf4j
public class MyServiceImpl_2 implements MyService {
    @Override
    public void sayHi(String name) {
        log.info("my name is "+name+" , Hello!");
    }
}
