package com.song.demo.controller;

import com.github.javafaker.Faker;
import com.song.demo.Service.StudentService;
import com.song.demo.config.ResultMsg;
import com.song.demo.entity.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: StudentController
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/5 15:28
 * @Version: 1.0
 **/
@Api(tags = "操作学生类")
@Slf4j
@RestController
@RequestMapping(value = "student")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @ApiOperation(value = "批量保存信息",notes ="批量保存")
    @GetMapping(value = "/saveall/{number}")
    public String saveAll(@PathVariable("number")Integer number){
        long startTime = System.currentTimeMillis();
        Faker faker=new Faker(Locale.CHINA);
        List<Student> myList=new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Student stu=new Student();
            stu.setName(faker.name().name());
            stu.setAge((int)Math.random()*100);
            Date date=new Date();
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE,-3);
            Date time = cal.getTime();
            stu.setBirthday(time);
            myList.add(stu);
        }
        studentService.saveAll(myList);
        long endTime = System.currentTimeMillis();
        log.info("保存用时：{}",endTime-startTime);
        return "success";
    }



    @ApiOperation(value = "保存信息",notes ="保存")
    @PostMapping(value = "/save")
    public String save(@RequestBody Student student){
        Student student1 = studentService.saveResource(student);
        if(student1!=null){
            return "success";
        }else{
            return "fail";
        }
    }

    @ApiOperation(value = "删除信息",notes ="删除")
    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id") String id){
        boolean b = studentService.deleteResource(id);
        if(b){
            return "success";
        }else{
            return "fail";
        }
    }

    @ApiOperation(value = "更新信息",notes = "更新")
    @PutMapping(value = "/update/{id}")
    public String update(@PathVariable(value = "id")String id,@RequestBody Student student){
        boolean b = studentService.updateResource(id, student);
        if(b){
            return "success";
        }else{
            return "fail";
        }
    }


    @ApiOperation(value = "根据id查询",notes = "查询单个")
    @GetMapping(value = "/findById/{id}")
    public Student findById(@PathVariable("id")String id){
       return studentService.findById(id);
    }


    @ApiOperation(value = "查询所有",notes = "查全部")
    @GetMapping(value = "/findAll")
    public ResultMsg findAll(){
        List<Student> all = studentService.findAll(0);
        JSONArray jsonArray = JSONArray.fromObject(all);
        String s = jsonArray.toString();
        System.out.println(s);
        ResultMsg resultMsg=new ResultMsg(10000,"success",all);
        return resultMsg;
    }


    @ApiOperation(value = "查询所有",notes = "查全部")
    @GetMapping(value = "/findByAgeAndBirthdayAfter/{age}")
    public Set<Student> findByAgeAndBirthdayAfter(@PathVariable("age")Integer age){
        return studentService.findByAgeAndBirthdayAfter(age,new Date());
    }

    @ApiOperation(value = "查询生日小于当前时间的学生",notes = "按生日查")
    @GetMapping(value = "/findByDate")
    public Set<Student> findByDate(){
        Date myDate=new Date();
        return studentService.findByDate(myDate);
    }


    @ApiOperation(value = "查询生日是当前时间近N天的学生",notes = "查询近N天数据")
    @GetMapping(value = "/findByDay/{days}")
    public List<Student> findByDay(@PathVariable("days")Integer days){
        return studentService.findByDay(days);
    }


    @GetMapping(value = "/testTime/time")
    public List<Integer> testTime(){
        long startTime = System.currentTimeMillis();
        List<Student> all = studentService.findAll(30);
        int size = all.size();
        Map<String,Integer> myMap=new LinkedHashMap<>();
        //log.info("2天前的时间：{}",day2ago);
        for (int j=0;j<30;j++) {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date date=new Date();
            log.info("当前时间：{}",sdf.format(date));
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE,-j);
            Date time = cal.getTime();
            String day2ago = sdf.format(time);
            log.info("{}天前的时间：{}",j,day2ago);
            log.info("=========={}==========",day2ago);
            int i=0;
            for (Student student : all) {
                Date birthday = student.getBirthday();
                String studentBirthday = sdf.format(birthday);
                if(studentBirthday.equals(day2ago)){
                    i+=1;
                    log.info("学生姓名：{}   学生生日：{}",student.getName(),student.getBirthday());
                }else{

                }
                log.info("当前下标：{},当前下标对应的值：{}",j,i);
                myMap.put(j+"",i);

            }
        }
        log.info("数据量：{}",size);
        log.info("map:{}",myMap);
        Set<String> strings = myMap.keySet();
        log.info("==>：{}",strings);
        List<Integer> mylist=new ArrayList<>();
        for (String string : strings) {
            Integer integer = myMap.get(string);
            mylist.add(integer);
        }
        log.info("==>：{}",mylist);
        long endTime = System.currentTimeMillis();
        log.info("统计用时：{} 毫秒",endTime-startTime);
        return mylist;
    }



    @GetMapping(value = "/testTime/time2/{n}")
    public List<Integer> testTime2(@PathVariable("n")Integer integer){
        if(integer==null||integer==0){
            integer=30;
        }
        long startTime = System.currentTimeMillis();
        List<Student> all = studentService.findAll(integer);
        SimpleDateFormat sdf=new SimpleDateFormat("MM-dd");
        Calendar calendar=Calendar.getInstance();
        String strs[]=new String[integer.intValue()];
        for(int i=1;i<=strs.length;i++){
            Date data=new Date();
            calendar.setTime(data);
            calendar.add(Calendar.DATE,-i);
            Date time = calendar.getTime();
            String str = sdf.format(time);
            strs[i-1]=str;
        }
        log.info("数据库数据：{}",all);
        log.info("#######################################");
        Integer myStrs[]=new Integer[integer.intValue()];
        for (int q = 0; q < strs.length; q++) {
            int j=0;
            String stander = strs[q];
            for (Student student : all) {
                String result = sdf.format(student.getBirthday());
                if(stander.equals(result)){
                    j+=1;
                    myStrs[q]=j;
                }else{
                    myStrs[q]=0;
                }
            }
        }
        long endTime = System.currentTimeMillis();
        log.info("用时：{}", endTime-startTime);
        List<Integer> integers = Arrays.asList(myStrs);
        return integers;
    }


}
