package com.song.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel(value = "付款码支付vo")
@Data
public class PayVo {
    @ApiModelProperty(value = "订单号",example = "youstwRJTwWyBfCng9KyeB",required = true)
    private String orderId;

    @ApiModelProperty(value = "支付宝授权码",example = "284189844235718424",required = true)
    private String authCode;

    @ApiModelProperty(value = "订单金额",example = "2.134",required = true)
    private BigDecimal totalAmount;

}
