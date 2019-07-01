package com.song.demo.controller;

import com.song.demo.Service.DuolabaoService;
import com.song.demo.constant.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: DuolabaoController
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/6/27 14:39
 * @Version: 1.0
 **/
@RestController
@RequestMapping(value = "duolaba")
@Api(tags = "哆啦宝支付")
@Slf4j
public class DuolabaoController {

    @Autowired
    private DuolabaoService duolabaoService;

    @GetMapping(value = "/payCreate/{requestNum}")
    @ApiOperation(value = "哆啦宝创建支付订单",notes = "支付订单")
    public String payCreate(@PathVariable("requestNum")String requestNum){
        Result result = duolabaoService.payCreate(requestNum);
        log.info("支付订单-controller=>>"+result.toString());
        return result.getMessage();

    }

    @GetMapping(value = "/urlCreate/{requestNum}")
    @ApiOperation(value = "哆啦宝创建支付链接",notes = "支付链接")
    public String urlCreate(@PathVariable("requestNum")String requestNum){
        Result result = duolabaoService.createPayUrl(requestNum);
        log.info("支付链接-controller=>>"+result.toString());
        return result.getMessage();
    }

    @GetMapping(value = "/refund/{requestNum}")
    @ApiOperation(value = "哆啦宝全部退款",notes = "全部退款")
    public String refund(@PathVariable("requestNum")String requestNum){
        Result result = duolabaoService.refund(requestNum);
        log.info("退款-controller=>>"+result.toString());
        return result.getMessage();
    }

    @GetMapping(value = "/refund/{requestNum}/{refundPartAmount}")
    @ApiOperation(value = "哆啦宝部分退款",notes = "部分退款")
    public String refundPart(@PathVariable("requestNum")String requestNum,@PathVariable("refundPartAmount")String refundPartAmount){
        Result result = duolabaoService.refundPart(requestNum,refundPartAmount);
        log.info("部分退款-controller=>>"+result.toString());
        return result.getMessage();
    }


    @GetMapping(value = "query/{requestNum}")
    @ApiOperation(value = "哆啦宝支付结果查询",notes = "查询结果")
    public String query(@PathVariable(value = "requestNum")String requestNum){
        Result result = duolabaoService.payResult(requestNum);
        log.info("查询-controller=>>"+result.toString());
        return result.getMessage();
    }

    @GetMapping(value = "/myNotify")
    @ApiOperation(value = "接收哆啦宝支付通知",notes = "接收异步通知")
    public void duolabaoNotify(@RequestParam(value = "requestNum")String requestNum,@RequestParam(value = "status")String status,@RequestParam(value = "orderAmount")String orderAmount){
        System.out.println("========接收哆啦宝异步通知========");
        log.info("订单编号："+requestNum);
        log.info("支付状态："+status);
        log.info("支付金额："+orderAmount);
    }

}
