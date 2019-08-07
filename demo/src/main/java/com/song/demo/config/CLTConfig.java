package com.song.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: CLTConfig
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/8/7 16:53
 * @Version: 1.0
 **/
@Component
@Data
@ConfigurationProperties(prefix = "clt")
public class CLTConfig {
    private String privateKey;
}
