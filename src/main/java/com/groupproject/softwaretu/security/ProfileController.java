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
    public String saveProfile(RegistrationForm form, Model model,
                              @RequestParam(name = "newPassword") String newPassword){
        // ifform.getPassword() == ""
        // log.info(""+());

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (passwordEncoder.matches(form.getPassword(), user.getPassword())){
            user.setUsername(form.getUsername());
            user.setFullName(form.getFullName());
            user.setEmail(form.getEmail());

            if (!newPassword.equals("")){
                user.setPassword(passwordEncoder.encode(newPassword));
            }
            userRepository.save(user);
            return "redirect:/profile";

        } else {
            return "redirect:/profile/edit?error";
        }





    }

    @PostMapping("/user/delete")
    public String deleteUser(@RequestParam(name="userId") Long id) {
        
        userRepository.deleteById(id);
        
        return "redirect:/admin";
    }
    
}
