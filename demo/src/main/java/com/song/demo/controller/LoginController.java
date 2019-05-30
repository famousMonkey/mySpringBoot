package com.song.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping(value = "login")
public class LoginController {

    @GetMapping(value = "index")
    public String toLogin(){
        return "index";
    }

    @GetMapping(value = "/index_1")
    public String Login(HttpServletRequest request){
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        request.setAttribute("userName",name);
        HttpSession session = request.getSession();
        session.setAttribute("pwd",password);
        return "success";
    }
}
