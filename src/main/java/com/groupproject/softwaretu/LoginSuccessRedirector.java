package com.groupproject.softwaretu;

import com.groupproject.softwaretu.security.User;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class LoginSuccessRedirector {


    @GetMapping("login/redirect")
    public String loginRedirect(){

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getRole().equals("ADMIN")){
            return "redirect:/manage/tutorials";
        }
        else{
            return "redirect:/";
        }
        
    }
}
