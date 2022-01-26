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

import javax.validation.Valid;
import org.springframework.validation.Errors;


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
    public String saveProfile(
                                        @Valid User user, 
                                        Errors error, 
                                        Model model,
                                        RegistrationForm form,
                              @RequestParam(name = "newPassword") String newPassword){
        // ifform.getPassword() == ""
        // log.info(""+());

        if (error.hasErrors()){
            return "editProfile";
        }

        User currentuser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (passwordEncoder.matches(user.getPassword(), currentuser.getPassword())){
            currentuser.setUsername(user.getUsername());
            currentuser.setFullName(user.getFullName());
            currentuser.setEmail(user.getEmail());

            if (!newPassword.equals("")){
                currentuser.setPassword(passwordEncoder.encode(newPassword));
            }
            userRepository.save(currentuser);
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
