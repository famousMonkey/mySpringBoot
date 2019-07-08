package com.song.demo.Service.impl;

import com.song.demo.Service.MyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: MyServiceImpl_1
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/1 10:21
 * @Version: 1.0
 **/
@Primary
@Service
@Slf4j
public class MyServiceImpl_1 implements MyService {
    @Override
    public void sayHi(String name) {
        log.info("my name is "+name+" , Hi!");
    }

    @Override
    public void introduce(String param) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long timeMillis = System.currentTimeMillis();
        System.out.println(param+" 异步调用 "+timeMillis);
    }


}
