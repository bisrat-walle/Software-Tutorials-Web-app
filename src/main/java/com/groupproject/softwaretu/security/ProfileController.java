package com.groupproject.softwaretu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ProfileController {

    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/profile")
    public String getProfile(Model model){
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
        if (user.getPassword() == bCryptPasswordEncoder.encode(form.getPassword())){
            log.info("user "+user);
            // userRepository.save(user);
        } else {
            model.addAttribute("error", "incorrect password");
            return "editProfile";
        }

        


        return "redirect:/profile";
    }
}
