package com.song.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: DlbConfig
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/12 13:51
 * @Version: 1.0
 **/
@Component
@Data
@ConfigurationProperties(prefix = "dlb")
public class DlbConfig {

    private String accessKey;
    private String secretKey;
    private String customerNum;
    private String shopNum;
    private String callbackUrl;

}
