package com.song.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: MyAsync
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/9/12 16:23
 * @Version: 1.0
 **/
@Component
public class MyAsync {

    private static final Logger log= LoggerFactory.getLogger(MyAsync.class);

    @Async
    public void doSomething(String ss) throws InterruptedException {
        log.info("异步方法开始");
        TimeUnit.SECONDS.sleep(10);
        System.out.println("ing========>..."+ss);
        log.info("异步方法结束");
    }


}
