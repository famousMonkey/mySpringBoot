package com.song.demo.entity;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @ClassName: Woman
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/8/26 16:43
 * @Version: 1.0
 **/
@Data
public class Woman implements Serializable {

    private String id;

    //@NotNull(message = "名字为null")
    @NotEmpty(message = "名字为null")
    private String name;

}
