package com.song.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.javafaker.Faker;
import com.song.demo.config.MyAsync;
import com.song.demo.config.MyConfig;
import com.song.demo.config.MyConstant;
import com.song.demo.config.ResultMsg;
import com.song.demo.constant.MyResource;
import com.song.demo.constant.MyResource2;
import com.song.demo.constant.MyResource3;
import com.song.demo.constant.Result;
import com.song.demo.entity.*;
import com.song.demo.util.CopyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
@RequestMapping(value = "login")
@Api(tags = "练习接口")
public class LoginController {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MyResource myResource;

    @Autowired
    private MyResource2 myResource2;

    @Autowired
    private MyResource3 myResource3;

    @Autowired
    private MyConfig myConfig;

    @Autowired
    private MyAsync myAsync;


    private Integer myType;


    @GetMapping(value = "/testException")
    @ApiOperation(value = "测试全局异常",notes = "全局异常")
    @ResponseBody
    public ResultMsg testException(@RequestParam(value = "tag",required = false)String tag){
        if("123".equalsIgnoreCase(tag)){
            return new ResultMsg(10000,"success");
        }
        if("456".equalsIgnoreCase(tag)){
            int i = Integer.parseInt("monkey");
        }
        if("789".equalsIgnoreCase(tag)){
            String name=null;
            int length = name.length();
            log.info("长度：{}",length);
        }
        return new ResultMsg(10000,"success");
    }

    @GetMapping(value = "myValue3")
    @ResponseBody
    public String myValue3(){
        List<String> hobby = myResource3.getHobby();
        for (String s : hobby) {
            System.out.println("--> "+s);
        }
        return myResource3.toString();
    }


    @GetMapping(value = "myValue2")
    @ResponseBody
    public String myValue2(){
        List<String> myList = myResource2.getResource();
        Map<String, String> map = myResource2.getMap();
        System.out.println("==========list===========");
        for (String s : myList) {
            System.out.println("----> "+s);
        }
        System.out.println("==========map===========");
        for (String s : map.keySet()) {
            System.out.println("----> "+s+" = "+map.get(s));
        }
        return myResource2.toString();
    }


    @GetMapping(value = "myValue")
    @ResponseBody
    public String myValue(){
        return myResource.toString();
    }


    @GetMapping(value = "/check")
    public void check(){
        System.out.println("版本回退联系");
        log.info("=======>>>>>>>>>========");
        String s = request.getHeader("User-Agent");
        log.info(">>>"+s);
    }


    @ResponseBody
    @GetMapping(value = "/getip")
    public String getIpAddress() {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            log.info("Proxy-Client-IP ip: {}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.info("WL-Proxy-Client-IP ip: {}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.info("HTTP_CLIENT_IP ip: {}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.info("HTTP_X_FORWARDED_FOR ip: {}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            log.info("X-Real-IP ip: {}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            log.info("getRemoteAddr ip: {}", ip);
        }
        return ip;
    }



    @ResponseBody
    @GetMapping(value = "/allpay")
    public String allPay(){
        log.info("===== \n"+request.getHeader("user-agent")+"\n===== ");
        return request.getHeader("user-agent");
    }

    @ResponseBody
    @GetMapping(value = "/index")
    public String Login(HttpServletRequest request){
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        log.info("password:"+password);
        return "Hello "+name;
    }

    @GetMapping(value = "/tologin")
    public String login(){
        return "login";
    }

    @GetMapping(value = "/success")
    public String success(){
        return "success";
    }

    @ResponseBody
    @GetMapping(value = "/mylogin")
    public Result mylogin(@RequestParam(value = "name")String name){
        log.info("===="+name);
        Result result=new Result();
        if(StringUtils.isBlank(name)){
            result.setSuccess(false);
            result.setMessage("用户名为空");
            return result;
        }else{
            if("123".equals(name)){
                result.setSuccess(true);
                return result;
            }else{
                result.setSuccess(false);
                result.setMessage("用户名错误");
                return result;
            }
        }
    }

    @GetMapping(value = "/redis/{id}")
    @ResponseBody
    public ResultMsg myRedis(@PathVariable("id")String id){
        String key="my:login:id:"+id;
        stringRedisTemplate.opsForValue().set(key,"123456",30,TimeUnit.MINUTES);
        String myKey="my.myLogin.id."+id;
        stringRedisTemplate.opsForValue().set(myKey,"helloWorld",30,TimeUnit.MINUTES);
        return new ResultMsg(10000,"SUCCESS");
    }

    @GetMapping(value = "/getvalue")
    public String getRedisKey(){
        log.info("我被调用了。。。");
        if(stringRedisTemplate.hasKey("wwwsssaaaddd")){
            return "index";
        }else{
            return "fail";
        }
    }

    @GetMapping(value = "/init/{type}")
    @ResponseBody
    public Result initType(@PathVariable("type")Integer type){
        myType=type;
        String str;
        switch (type){
            case 1:
               str="整型";
               break;
            case 2:
                str="字符型";
                break;
            case 3:
                str="对象";
                break;
            default:
                str="未知";
        }
        System.out.println("type="+str);
        Result result=new Result();
        if("未知".equals(str)){
            result.setSuccess(false);
            result.setMessage("获取类型失败");
        }else{
            result.setSuccess(true);
            result.setMessage(str);
        }
        return result;
    }

    @GetMapping(value = "/out")
    @ResponseBody
    public String outType(){
        if(myType==null){
            return "myType is null";
        }else if(myType==1){
            return "myType = 1";
        }else{
            return "myType = other";
        }
    }

    @GetMapping(value = "/testFilter")
    public String testFilter(@RequestParam(name = "aa") String value){
        request.setAttribute("bb",value);
        log.info("bb的值="+request.getAttribute("bb").toString());
        return "login";
    }

    @ResponseBody
    @GetMapping(value = "/testFaker")
    public String testFaker(){
        Faker faker=new Faker(Locale.CHINA);
        System.out.println("名次\t姓名\t成绩");
        for (int i = 0; i < 10; i++) {
            String name = faker.name().fullName();
            Integer result = faker.random().nextInt(1, 100);
            if(name.length()==2){
                name=name+" ";
            }
            System.out.println(" "+(i+1)+"\t   "+name+"\t "+result);
        }
        return faker.university().name();
    }

    @ResponseBody
    @GetMapping(value = "/testCopy")
    public String testCopy(){
        Teacher teacher=new Teacher();
        teacher.setId("001");
        teacher.setName("孙悟空");
        teacher.setAddress("");
        Teacher teacher1=new Teacher();
        CopyUtil.copyNonNullProperties(teacher,teacher1);
        System.out.println("11111--"+teacher1.toString());
        System.out.println("22222--"+JSON.toJSONString(teacher1, SerializerFeature.WriteMapNullValue));
        return JSON.toJSONString(teacher1);
    }


    @ResponseBody
    @GetMapping(value = "/testEnum/{type}")
    public ResultMsg testEnum(@PathVariable(value = "type")Integer type){
        if(type==0){
            Student student=null;
            Optional<Student> optional = Optional.of(student);
            //Optional<Student> optional = Optional.ofNullable(student);
            Student myStudent=new Student();
            myStudent.setId("003");
            myStudent.setName("天蓬元帅");
            myStudent.setAge(300);
            Student stuResult = optional.orElse(myStudent);
            ResultMsg result=new ResultMsg(MyConstant.SUCCESS.getCode(),MyConstant.SUCCESS.getMessage(),stuResult);
            return result;
        }else if(type==1){
            log.info("枚举类：{}",MyConstant.valueOf("MESSAGE_IS_NULL").toString());
            ResultMsg result=new ResultMsg(MyConstant.MESSAGE_IS_NULL.getCode(),MyConstant.MESSAGE_IS_NULL.getMessage());
            return result;
        }else if(type==2){
            Student student=new Student();
            student.setId("500");
            student.setName("齐天大圣");
            student.setAge(527);
            ResultMsg result=new ResultMsg(MyConstant.ILLEGAL_REQUEST_PARAMETERS.getCode(),MyConstant.ILLEGAL_REQUEST_PARAMETERS.getMessage(),student);
            return result;
        }else{
            Teacher teacher=new Teacher();
            teacher.setId("001");
            teacher.setName("金蝉子");
            teacher.setAge(27);
            teacher.setAddress("大唐长安");
            ResultMsg result=new ResultMsg(MyConstant.NULL_POINTER_EXCEPTION.getCode(),MyConstant.NULL_POINTER_EXCEPTION.getMessage(),teacher);
            return result;
        }
    }

    @ResponseBody
    @PostMapping(value = "/testPerson")
    public String testPerson(@Validated @RequestBody Person person, BindingResult result){
        if(result.hasErrors()){
            return result.getFieldError().getDefaultMessage();
        }

        Man man = person.getMan();
        Woman woman = person.getWoman();
        log.info("男人：{}",man);
        log.info("女人：{}",woman);
        return JSON.toJSONString(person);
    }


    @ResponseBody
    @PostMapping(value = "/testTeacher")
    public ResultMsg testTeacher(@RequestBody@Valid Teacher teacher,BindingResult result){
        if(result.hasErrors()){
            return new ResultMsg(10022,result.getFieldError().getDefaultMessage());
        }else{
            Teacher t1=new Teacher();
            System.out.println(t1.toString());
            return new ResultMsg(10000,"success",teacher);
        }
    }

    @ResponseBody
    @GetMapping(value = "/testMyConfig")
    public ResultMsg testMyConfig(){
        Set<String> type = myConfig.getType();
        return new ResultMsg(10000,"success",type);
    }


    @ResponseBody
    @GetMapping(value = "/testAsync/{ss}")
    public String testAsync(@PathVariable("ss")String ss) throws InterruptedException {
        myAsync.doSomething(ss);
        //TimeUnit.SECONDS.sleep(2);
        System.out.println("abc");
        System.out.println("def");
        System.out.println("ghi");
        return ss;
    }






}
