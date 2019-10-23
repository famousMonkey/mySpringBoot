package com.song.demo;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
