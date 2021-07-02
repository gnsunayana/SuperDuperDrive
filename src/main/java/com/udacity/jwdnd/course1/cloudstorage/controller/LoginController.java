package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

@Controller
@RequestMapping("/login")
public class LoginController {

    @PostConstruct
    public void postConstruct(){
        System.out.println("LoginController has been created");
    }


    @RequestMapping
    public String getLoginView(){
        System.out.println("In getLoginView");
        return "login";
    }




}
