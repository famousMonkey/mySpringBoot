package com.song.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @ClassName: DownLloadFile
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/7/9 15:53
 * @Version: 1.0
 **/
@RestController
@Api(tags = "文件下载")
@RequestMapping(value = "file")
public class DownloadFile {

    @GetMapping(value = "/download")
    @ApiOperation(value = "文件下载",notes = "文件下载")
    public File download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File("d:\\Users\\meatball\\system\\file\\2019-07-09\\1562659145954.xls");
        //1.获取要下载的文件的绝对路径
        String realPath = "d:\\Users\\meatball\\system\\file\\2019-07-09\\1562659145954.xls";
        //2.获取要下载的文件名
        String fileName = realPath.substring(realPath.lastIndexOf(File.separator)+1);
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
        response.addHeader("Content-Disposition","attachment;filename=" + new String(fileName.getBytes(),"utf-8"));
        //获取文件输入流
        InputStream in = new FileInputStream(realPath);
        int len = 0;
        byte[] buffer = new byte[1024];
        OutputStream out = response.getOutputStream();
        while ((len = in.read(buffer)) > 0) {
            //将缓冲区的数据输出到客户端浏览器
            out.write(buffer,0,len);
        }
        in.close();
        return file;

    }


}
