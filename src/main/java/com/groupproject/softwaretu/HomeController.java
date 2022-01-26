package com.groupproject.softwaretu;

import com.groupproject.softwaretu.enrollement.EnrollementRepository;
import com.groupproject.softwaretu.project.ProjectRepository;
import com.groupproject.softwaretu.security.User;
import com.groupproject.softwaretu.security.UserRepository;
import com.groupproject.softwaretu.tutorial.TutorialRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;


@Controller
@Slf4j
public class HomeController {

    @Autowired
    UserRepository userRepository;


    @Autowired
    EnrollementRepository enrollementRepository;

    @Autowired
    ProjectRepository projectRepository;
	
	@Autowired
    TutorialRepository tutorialRepository;

    @GetMapping("/")
    public String home(Model model) {

        Boolean loggedIn = true;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addAttribute("user", user);
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

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        model.addAttribute("loggedIn", loggedIn);
		model.addAttribute("totalEnrollement", enrollementRepository.findAll());
		model.addAttribute("totalClient", userRepository.getUserOfType("CLIENT"));
		model.addAttribute("totalInstructor", userRepository.getUserOfType("INSTRUCTOR"));

        List<User> excludingAdmin = new ArrayList<>();

        userRepository.findAll().forEach(
                i -> {
                    if(!i.getUsername().equals(user.getUsername())){
                        excludingAdmin.add(i);
                    }
                }
        );

        model.addAttribute("users", excludingAdmin);
		model.addAttribute("totalProject", projectRepository.findAll());
		model.addAttribute("totalTutorial", tutorialRepository.findAll());
        
        return "admin";
    }
	
	@GetMapping("/manage/clients")
    public String clients(Model model) {

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
		model.addAttribute("totalTutorial", tutorialRepository.findAll());
        
        return "admin-clients";
    }
	
	@GetMapping("/manage/instructors")
    public String instructors(Model model) {

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
		model.addAttribute("totalTutorial", tutorialRepository.findAll());
        
        return "admin-instructors";
    }
	
	
	@GetMapping("/manage/tutorials")
    public String tutorials(Model model) {

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
		model.addAttribute("totalTutorial", tutorialRepository.findAll());
        
        return "admin-tutorials";
    }
}
