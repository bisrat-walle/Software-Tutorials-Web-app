package com.groupproject.softwaretu;

import com.groupproject.softwaretu.enrollement.EnrollementRepository;
import com.groupproject.softwaretu.project.ProjectRepository;
import com.groupproject.softwaretu.security.User;
import com.groupproject.softwaretu.security.UserRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


@Controller
@Slf4j
public class HomeController {

    @Autowired
    UserRepository userRepository;


    @Autowired
    EnrollementRepository enrollementRepository;

    @Autowired
    ProjectRepository projectRepository;

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
	
	@GetMapping("/admin")
    public String admin(Model model) {

        Boolean loggedIn = true;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e){
            loggedIn = false;
        }

        
        
        model.addAttribute("loggedIn", loggedIn);
		model.addAttribute("totalEnrollement", enrollementRepository.findAll());
		model.addAttribute("totalClient", userRepository.getUserOfType("CLIENT"));
		model.addAttribute("totalInstructor", userRepository.getUserOfType("INSTRUCTOR"));
		model.addAttribute("users", userRepository.findAll());
		model.addAttribute("totalProject", projectRepository.findAll());
        
        return "admin";
    }
}
