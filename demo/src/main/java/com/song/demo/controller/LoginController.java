package com.song.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "login")
public class LoginController {

    @GetMapping(value = "/index")
    public String Login(HttpServletRequest request){
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        log.info("password:"+password);
        return "Hello "+name;
    }
}
