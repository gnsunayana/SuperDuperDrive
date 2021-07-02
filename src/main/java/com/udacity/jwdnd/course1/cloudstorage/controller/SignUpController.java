package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    public final UserService userService;

    public SignUpController(UserService userService){
        this.userService= userService;
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("SignupController bean created");
    }

    @GetMapping
    public String signupView(){
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model){
        String signupError= null;

        if(!userService.isUsernameAvailable(user.getUserName())){
              signupError= "The username already exists";
        }

        if(signupError == null){
            int rowsAdded= userService.createUser(user);
            if(rowsAdded <0){
                signupError= "There was an error signing you up. Please try again";
            }
        }

        if(signupError == null){
            model.addAttribute("signUpSuccess",true);
        }
        else{
            model.addAttribute("signupError",signupError);
        }
        return "signup";
    }

}
