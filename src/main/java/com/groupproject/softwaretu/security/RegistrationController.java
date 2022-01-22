package com.groupproject.softwaretu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String register(){
        return "signup";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form){
        userRepository.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }

}
