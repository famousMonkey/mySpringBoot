package com.song.demo.controller;

import com.alibaba.fastjson.JSON;
import com.song.demo.Service.DuolabaoService;
import com.song.demo.constant.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping(value = "/payCreate/{requestNum}/{authId}")
    @ApiOperation(value = "哆啦宝创建支付订单",notes = "商户直接调用此接口，返回调起支付所需支付要素，如微信和微信小程序可以直接调起微信支付。")
    public String payCreate(@PathVariable("requestNum")String requestNum,@PathVariable("authId")String authId){
        Map<String, String> result = duolabaoService.payCreate(requestNum,authId);
        log.info("支付订单-controller=>>"+result.toString());
        return JSON.toJSONString(result);

    }

    @GetMapping(value = "/urlCreate/{requestNum}/{amount}")
    @ApiOperation(value = "哆啦宝创建支付链接",notes = "商户通过该接口创建交易的支付链接，可通过扫码进入哆啦宝固定金额支付页面。")
    public String urlCreate(@PathVariable("requestNum")String requestNum,@PathVariable("amount") String amount){
        Result result = duolabaoService.createPayUrl(requestNum,amount);
        log.info("支付链接-controller=>>"+result.toString());
        return result.getMessage();
    }

    @GetMapping(value = "/refund/{requestNum}")
    @ApiOperation(value = "哆啦宝全部退款",notes = "当交易发生之后一段时间内，由于商户的原因需要退款时，商户可以通过退款接口将退交易款还给交易者，哆啦宝将在收到退款请求并且验证成功之后，按照退款规则将交易款按原路退至交易者帐号上。总退款金额不能超过用户实际交易金额。")
    public String refund(@PathVariable("requestNum")String requestNum){
        Result result = duolabaoService.refund(requestNum);
        log.info("退款-controller=>>"+result.toString());
        return result.getMessage();
    }

    @GetMapping(value = "/refund/{requestNum}/{refundPartAmount}")
    @ApiOperation(value = "哆啦宝部分退款",notes = "当交易发生之后一段时间内，由于商户的原因需要部分退款时，商户可通过该退款接口将需退款金额退还给交易者，哆啦宝将在收到退款请求并且验证成功之后，按照部分退款规则将需退款金额按原路退至交易者。交易时间不在系统退款周期内的订单将无法提交退款。部分退款支持单笔交易分多次退款(最多两次),累计退款金额不能超过用户实际支付金额。")
    public String refundPart(@PathVariable("requestNum")String requestNum,@PathVariable("refundPartAmount")String refundPartAmount){
        Result result = duolabaoService.refundPart(requestNum,refundPartAmount);
        log.info("部分退款-controller=>>"+result.toString());
        return result.getMessage();
    }


    @GetMapping(value = "query/{requestNum}")
    @ApiOperation(value = "哆啦宝支付结果查询",notes = "该接口提供所有哆啦宝用户支付订单的查询，商户可以通过该接口主动查询订单状态，完成下一步的业务逻辑。 需要调用查询接口的情况： 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知。")
    public String query(@PathVariable(value = "requestNum")String requestNum){
        Result result = duolabaoService.payResult(requestNum);
        log.info("查询-controller=>>"+result.toString());
        return result.getMessage();
    }

    @GetMapping(value = "/passive/{requestNum}/{authCode}/{amount}")
    @ApiOperation(value = "哆啦宝商户被扫接口",notes="该接口提供商户收银系统或哆啦宝商户APP扫用户手机付款码进行支付时使用， 用户打开微信(支付宝)付款界面，商户收银系统或哆啦宝商户APP扫描用户付款二维码或条码进行收款。")
    public String passive(@PathVariable(value = "requestNum")String requestNum,@PathVariable(value = "authCode")String authCode,@PathVariable(value = "amount")String amount){
        Result result = duolabaoService.passive(requestNum, authCode, amount);
        log.info("付款码-controller=>>"+result.toString());
        return result.getMessage();
    }

    @GetMapping(value = "close/{requestNum}")
    @ApiOperation(value = "哆啦宝支付订单关闭",notes ="只有未支付成功的订单可以关闭，关闭之后的订单无法再支付成功。")
    public String close(@PathVariable(value = "requestNum")String requestNum){
        Result result = duolabaoService.payResult(requestNum);
        log.info("关闭-controller=>>"+result.toString());
        return result.getMessage();
    }


    @GetMapping(value = "cancel/{requestNum}")
    @ApiOperation(value = "哆啦宝支付订单撤销",notes ="只针对被扫的处理中的订单, 只能在支付15s后调用 如果此订单用户支付失败，会将此订单撤销；如果用户支付成功，会将此订单资金退还给用户。 注意：只有发生支付系统超时或者支付结果未知时可调用撤销，其他正常支付的单如需实现相同功能请调用退款API。")
    public String cancel(@PathVariable("requestNum")String requestNum){
        Result result = duolabaoService.cancel(requestNum);
        log.info("撤销-controller=>>"+result.toString());
        return result.getMessage();
    }


    @GetMapping(value = "/myNotify")
    @ApiOperation(value = "哆啦宝支付完成通知",notes = "该接口提供所有哆啦宝商户对接，支付成功后哆啦宝系统会将订单状态的结果通知到代理商回调地址中。如果调用回调地址失败，哆啦宝系统会重新发起请求，重试5次，每次间隔5秒，5次后仍未成功，则不重试。")
    public void duolabaoNotify(@RequestParam(value = "requestNum",required = false)String requestNum,@RequestParam(value = "status",required = false)String status,@RequestParam(value = "orderAmount",required = false)String orderAmount){
        System.out.println("========接收哆啦宝异步通知========");
        log.info("订单编号："+requestNum);
        log.info("支付状态："+status);
        log.info("支付金额："+orderAmount);
    }

}
