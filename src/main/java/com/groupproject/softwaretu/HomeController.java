package com.groupproject.softwaretu;

import com.groupproject.softwaretu.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


@Controller
@Slf4j
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {

        Boolean loggedIn = true;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e){
            loggedIn = false;
        }
        model.addAttribute("loggedIn", loggedIn);
        return "home";
    }
}
