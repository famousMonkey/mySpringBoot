package com.song.demo.config;

import com.song.demo.Service.CLTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.TimerTask;

/**
 * @ClassName: MyTask
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/8/8 10:53
 * @Version: 1.0
 **/
//@Component
@EnableScheduling
@Slf4j
public class MyTask extends TimerTask {

    @Autowired
    private CLTService cltService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Scheduled(fixedDelay = 10000L)
    @Override
    public void run() {
        log.info("!!!!!!!!!!!task start!!!!!!!!!");
        String ccSessionId=null;
        if(stringRedisTemplate.hasKey("clt.phoneNumber:17780611561")){
            String s = stringRedisTemplate.opsForValue().get("clt.phoneNumber:17780611561");
            ccSessionId=s;
            System.out.println("ccSessionId:"+ccSessionId);
            cltService.alive(ccSessionId);
        }
        log.info("!!!!!!!!!!!task end!!!!!!!!!");
    }
}
