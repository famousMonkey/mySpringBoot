package com.song.demo.controller;


import com.song.demo.Service.AliPayService;
import com.song.demo.vo.PayVo;
import com.song.demo.vo.PrecreateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequestMapping(value = "alipay")
@Controller
@Api(tags="支付操作接口")
public class AliPayController {

    @Autowired
    private AliPayService aliPayService;

    @ApiOperation(value = "支付宝扫码支付接口",notes = "订单金额单位元")
    @ResponseBody
    @PostMapping(value = "/precreate")
    public void precreate(@RequestBody PrecreateVo precreateVo){
        if(StringUtils.isBlank(precreateVo.getOrderId())){
            System.out.println("订单号为空");
        }
        BigDecimal totalAmount = precreateVo.getTotalAmount();
        BigDecimal myAmount = totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP);//两位小数，四舍五入
        String price=myAmount.toString();
        if(StringUtils.isBlank(price)){
            System.out.println("订单金额为空");
        }
        Boolean flag = aliPayService.precreate(precreateVo.getOrderId(), price);
        if(flag){
            System.out.println("调用成功");
        }else{
            System.out.println("调用失败");
        }
    }

    @ApiOperation(value = "支付宝付款码支付接口",notes = "付款码不能为空")
    @ResponseBody
    @PostMapping(value = "/pay")
    public void pay(@RequestBody PayVo payVo){
        if(StringUtils.isBlank(payVo.getAuthCode())){
            System.out.println("支付宝授权码为空");
        }
        if(StringUtils.isBlank(payVo.getOrderId())){
            System.out.println("订单号为空");
        }
        BigDecimal totalAmount = payVo.getTotalAmount();
        BigDecimal myAmount = totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP);//两位小数，四舍五入
        String price=myAmount.toString();
        if(StringUtils.isBlank(price)){
            System.out.println("订单金额为空");
        }
        Boolean flag = aliPayService.pay(payVo.getOrderId(), payVo.getAuthCode(), price);
        if(flag){
            System.out.println("支付成功");
        }else{
            System.out.println("支付失败");
        }
    }



}
