package com.song.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName RefundVo
 * @Description //TODO()
 * @Author 宋正健
 * @Date 2019/6/12 14:52
 * @Version 1.0
 **/

@ApiModel(value = "支付宝退款VO")
@Data
public class RefundVo {
    @ApiModelProperty(value = "订单号",example = "youstwRJTwWyBfCng9KyeB",required = true)
    private String orderId;

    @ApiModelProperty(value = "订单金额",example = "2.134",required = true)
    private BigDecimal amount;
}
