package com.groupproject.softwaretu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String getProfile(Model model){
        Boolean loggedIn = true;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e){
            loggedIn = false;
        }
        model.addAttribute("loggedIn", loggedIn);
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", (User) ob);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model){
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", (User) ob);
        return "editProfile";
    }

    @PostMapping("/profile/edit")
    public String saveProfile(RegistrationForm form, Model model){
        // ifform.getPassword() == ""
        // log.info(""+());

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getPassword() == passwordEncoder.encode(form.getPassword())){
            log.info("user "+user);
            // userRepository.save(user);
        } else {
            log.info("user "+user); 
            model.addAttribute("user", user);
            model.addAttribute("error", "incorrect password");
            return "redirect:/editProfile?error";
        }

        


        return "redirect:/profile";
    }

    @PostMapping("/user/delete")
    public String deleteUser(@RequestParam(name="userId") Long id) {
        
        userRepository.deleteById(id);
        
        return "redirect:/admin";
    }
    
}
