package com.song.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel(value = "扫码支付vo")
@Data
public class PrecreateVo {
    @ApiModelProperty(value = "订单号",example = "myOstwRJTwWyBfCng9KyeA",required = true)
    private String orderId;

    @ApiModelProperty(value = "订单金额",example = "2.134",required = true)
    private BigDecimal totalAmount;
}
