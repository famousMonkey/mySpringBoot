package com.song.demo.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: MyResource3
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/3 8:52
 * @Version: 1.0
 **/
@Configuration
@Data
@ConfigurationProperties(prefix = "monkey")
public class MyResource3 {

    private String name;
    private List<String> hobby;
    private Map<String,String> map;

}
