package com.song.demo.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: MyResource2
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/2 14:31
 * @Version: 1.0
 **/
@Component
@Data
@ConfigurationProperties(prefix = "your")
public class MyResource2 {

    private List<String> resource;
    private Map<String,String> map;

}
