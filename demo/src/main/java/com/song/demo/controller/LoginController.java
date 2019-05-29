package com.song.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "login")
public class LoginController {

    @GetMapping(value = "/index")
    public String Login(){
        return "SUCCESS";
    }
}
