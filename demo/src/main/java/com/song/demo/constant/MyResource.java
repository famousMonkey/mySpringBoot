package com.song.demo.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MyResource
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/2 14:15
 * @Version: 1.0
 **/
@Component
@Data
@ConfigurationProperties(prefix = "my")
public class MyResource {

    private String name;
    private Integer age;

}
