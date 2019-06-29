package com.song.demo.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.song.demo.Service.AliPayService;
import com.song.demo.util.readAliParamUtil;
import com.song.demo.vo.PayVo;
import com.song.demo.vo.PrecreateVo;
import com.song.demo.vo.TransferVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

@RequestMapping(value = "alipay")
@Controller
@Slf4j
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


    @ApiOperation(value = "支付宝转账接口",notes = "订单号不能为空")
    @ResponseBody
    @PostMapping(value = "/transfer")
    public void transfer(@RequestBody TransferVo transferVo){
        if(StringUtils.isBlank(transferVo.getOrderId())){
            System.out.println("订单号为空");
        }
        BigDecimal amount = transferVo.getAmount();
        BigDecimal myAmount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);//两位小数，四舍五入
        String price=myAmount.toString();
        if(StringUtils.isBlank(price)){
            System.out.println("订单金额为空");
        }
        Boolean flag = aliPayService.transfer(transferVo.getOrderId(), price,transferVo.getName());
        if(flag){
            System.out.println("支付成功");
        }else{
            System.out.println("支付失败");
        }

    }

    @ApiOperation(value = "支付宝退款接口",notes = "订单号不能为空，退款金额不能大于订单金额")
    @ResponseBody
    @GetMapping(value = "/redund/{orderId}")
    public void refund(@PathVariable("orderId")String orderId){
        if(StringUtils.isBlank(orderId)){
            System.out.println("订单号为空");
        }
        Boolean flag = aliPayService.refund(orderId);
        if(flag){
            System.out.println("退款成功");
        }else{
            System.out.println("退款失败");
        }
    }

    @ApiOperation(value = "app支付",notes = "app支付")
    @ResponseBody
    @GetMapping(value = "/app/{orderId}")
    public String app(@PathVariable("orderId")String orderId){
       return aliPayService.app(orderId,"1");
    }


    @ApiOperation(value = "手机网站支付",notes = "手机网站支付")
    @ResponseBody
    @GetMapping(value = "/wap/{orderId}")
    public String wap(@PathVariable("orderId")String orderId){
        return aliPayService.wapPay(orderId);
    }


    @ApiOperation(value = "支付宝异步通知接口")
    @ResponseBody
    @GetMapping(value = "/callback/notify")
    public String myNotify(@RequestParam Map<String,String> paramMap) throws Exception{
        log.info("异步回调进来了=====");
        //调用SDK验证签名
        boolean flag =
                AlipaySignature.rsaCheckV1(paramMap,
                        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxMgLRxy9650Bwy+/HSVofDQb0JCZuv0a+/u8SIOiOlCsjEodi9mmVQyUgQ7Kt8PdL0AWKOlM7eif3sBtJqw3pV/KYkQhN6W0seuFQeGRGUlwyoNhCAXxsrTxPLN1CrXiUTGel+tgUk1Nk12kAvn7Wj9jGHwjA9LxdLAlj5VAUpEuy+bhtHGPo+gDXv4GolQXi5WxJEX2uCQEYKGRv4wgUJxSz00QRUUws7sH9idsA83YP5fwkqEBe8TPq7rpagYviLQeWeykXWuhVuGOnlHEBNc3ySGf7kXjk5q4M3qVhUSlD2cD8AOnjcnEx3jrsChhhtsdmWzCXPXUL1OEcmUSVQIDAQAB",
                        "UTF-8",
                        "RSA2");
        log.info("验签是否成功="+flag);
        if(!flag){
            return "failure";
        }
        String trade_status = paramMap.get("trade_status");
        if ("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)){
            //支付成功，修改订单数据
            String out_trade_no = paramMap.get("out_trade_no");//支付订单号
            return "success";
        }else{
            return"failure";
        }
    }


    @ApiOperation(value = "支付宝页面跳转接口")
    @GetMapping(value = "/returnurl")
    public String returnUlr(HttpServletRequest request) throws AlipayApiException {
        log.info("--开始接收支付宝异步通知--");
        Map<String, String> map = readAliParamUtil.read(request);
        log.info("返回参数：\n"+map.toString());
        request.setAttribute("orderId",map.get("out_trade_no"));
        return "success";
    }



}
