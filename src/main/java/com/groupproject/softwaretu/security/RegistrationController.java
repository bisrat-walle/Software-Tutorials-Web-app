package com.groupproject.softwaretu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;
import org.springframework.validation.Errors;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String register(Model model){
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return "redirect:/";
        } catch (ClassCastException e){
            model.addAttribute("user", new User());
            return "signup";
        }
    }

    @PostMapping
    public String processRegistration(
                                        @Valid User user, 
                                        Errors error, 
                                        Model model,
                                        RegistrationForm form
                                        ){
        
        if (error.hasErrors()){
            return "signup";
        }
		userRepository.save(form.myToUser(user, passwordEncoder));
		return "redirect:/login";
		
        
        
    }

}
