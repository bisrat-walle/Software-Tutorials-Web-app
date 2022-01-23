package com.groupproject.softwaretu.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SigninController {
    @GetMapping("/login")
    public String login(){
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return "redirect:/";
        } catch (ClassCastException e){
            return "signin";
        }
        
    }
}
