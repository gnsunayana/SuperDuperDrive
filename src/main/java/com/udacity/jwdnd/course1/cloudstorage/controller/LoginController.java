package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

@Controller
@RequestMapping("/login")
public class LoginController {

    @PostConstruct
    public void postConstruct() {
    }


    @RequestMapping
    public String getLoginView() {
        return "login";
    }


}
