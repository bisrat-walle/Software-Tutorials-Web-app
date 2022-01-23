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
    public String register(){
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return "redirect:/";
        } catch (ClassCastException e){
            return "signup";
        }
    }

    @PostMapping
    public String processRegistration(RegistrationForm form, Model model){
		try{
			userRepository.save(form.toUser(passwordEncoder));
			return "redirect:/login";
		} catch(DataIntegrityViolationException ex){
			log.info("error "+ex.getMessage());
			model.addAttribute("Integ exception", ex.getMessage());
			return "redirect:/register?error";
		} catch(Exception e){
			log.info("Exception error "+e.getMessage());
			model.addAttribute("exception", e.getMessage());
			return "redirect:/register?error";
		}
        
        
    }

}
