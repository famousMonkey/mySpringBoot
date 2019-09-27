package com.song.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @ClassName: MyConfig
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/9/7 9:44
 * @Version: 1.0
 **/
@Data
@Component
@ConfigurationProperties(prefix = "account")
public class MyConfig {

    private Set<String> type;

}
