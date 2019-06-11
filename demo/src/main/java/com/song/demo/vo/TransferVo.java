package com.song.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName TransferVo
 * @Description //TODO()
 * @Author 宋正健
 * @Date 2019/6/11 14:27
 * @Version 1.0
 **/

@ApiModel(value = "支付宝转账VO")
@Data
public class TransferVo {

    @ApiModelProperty(value = "订单号",example = "wsustwRJTwWyBfCng9KyeA",required = true)
    private String orderId;

    @ApiModelProperty(value = "订单金额",example = "32.58",required = true)
    private BigDecimal amount;

    @ApiModelProperty(value = "付款方姓名",example = "孙悟空")
    private String name;

}
