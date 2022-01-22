package com.groupproject.softwaretu.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

    private UserRepository userRepository;

    @GetMapping("/profile")
    public String getProfile(Model model){
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", (User) ob);
        return "profile";
    }
}
