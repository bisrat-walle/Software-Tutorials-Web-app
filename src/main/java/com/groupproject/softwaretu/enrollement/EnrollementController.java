package com.groupproject.softwaretu.enrollement;

import com.groupproject.softwaretu.tutorial.Tutorial;
import com.groupproject.softwaretu.tutorial.TutorialRepository;
import com.groupproject.softwaretu.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@Slf4j
public class EnrollementController {


    @Autowired
    private EnrollementRepository enrollementRepository;

    @Autowired
    private TutorialRepository tutorialRepository;

    @PostMapping("/enroll")
    public String enroll(@Valid Enrollement newEnrollement, Errors errors, @RequestParam Long tutorialId) {

        if (errors.hasErrors()) {
            return "redirect:/tutorials/all";
        }

        log.info("Processing enrollement" + tutorialId);
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ( ob != null){
            newEnrollement.setClient((User) ob);
        }

        newEnrollement.setEnrolledAt(LocalDateTime.now());

        Tutorial tutorial= tutorialRepository.findByTutorialId(tutorialId);
        newEnrollement.setTutorial(tutorial);
        enrollementRepository.save(newEnrollement);

        return "redirect:/tutorials/enrolled";
    }
    
}
