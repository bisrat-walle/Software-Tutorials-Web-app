package com.groupproject.softwaretu.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SigninController {
    @GetMapping("/login")
    public String login(){
        return "signin";
    }
}
