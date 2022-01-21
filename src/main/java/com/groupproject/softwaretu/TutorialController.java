package com.groupproject.softwaretu;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import org.springframework.validation.Errors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/tutorials")
public class TutorialController {

    @Autowired
    private TutorialRepository tutorialRepository;

    @GetMapping("/all")
    public String allTutorials(Model model){
        
        Iterable<Tutorial> tutorials = tutorialRepository.findAll();
        model.addAttribute("tutorials", tutorials);
        return "tutorials";
    }

    @GetMapping("/enrolled")
    public String enrolledTutorials(){
        return "enrolledTutorials";
    }
	
	@GetMapping("/mytutorials")
    public String myTutorials(){
        return "myTutorials";
    }

    @GetMapping("/create")
    public String createTutorial(Model model){
        model.addAttribute("newTutorial", new Tutorial());
        return "createTutorial";
    }

    @PostMapping("/create")
    public String processTutorial(@Valid Tutorial newTutorial,  Errors errors) {

        if (errors.hasErrors()) {
            return "createTutorial";
        }   
        
        log.info("Processing "+newTutorial);
        tutorialRepository.save(newTutorial);
        return "redirect:/";
    }
	

}
