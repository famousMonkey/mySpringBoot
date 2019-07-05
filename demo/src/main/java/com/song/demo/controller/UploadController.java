package com.song.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName: UploadController
 * @Description: //TODO(文件上传)
 * @Author: 宋正健
 * @Date: 2019/7/5 14:00
 * @Version: 1.0
 **/
@Api(tags = "文件上传")
@RestController(value = "file")
@Slf4j
public class UploadController {

    @ApiOperation(value = "文件上传练习",notes = "文件上传")
    @PostMapping(value = "/upload")
    @ResponseBody
    public String upLoad(@RequestBody MultipartFile file) throws IOException {
        log.info("=====开始文件上传=====");
        if(file.isEmpty()){
            return "文件时空文件";
        }else{
            String contentType = file.getContentType();//文件类型
            String name = file.getName();//处理后的文件名称
            String originalFilename = file.getOriginalFilename();//源文件名
            long size = file.getSize();//文件大小
            log.info("上传后的文件名："+name);
            // 保存文件
            file.transferTo(new File("d://My/monkey-" + originalFilename));
            return String.format(file.getClass().getName() + "方式文件上传成功！\n文件名:%s,文件类型:%s,文件大小:%s", originalFilename, contentType,size);
        }
    }

}
