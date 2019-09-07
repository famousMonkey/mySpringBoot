package com.song.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {


    private static final Logger log= LoggerFactory.getLogger(DemoApplication.class);

    @Test
    public void contextLoads() {

        String msg="这是一条测试信息！";

        log.trace("trace {}",msg);
        log.debug("debug {}",msg);
        log.info("info {}",msg);
        log.warn("warn {}",msg);
        log.error("error {}",msg);

    }

}
