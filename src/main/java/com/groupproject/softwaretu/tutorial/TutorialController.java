package com.groupproject.softwaretu.tutorial;

import com.groupproject.softwaretu.enrollement.Enrollement;
import com.groupproject.softwaretu.enrollement.EnrollementRepository;
import com.groupproject.softwaretu.project.Project;
import com.groupproject.softwaretu.project.ProjectRepository;
import com.groupproject.softwaretu.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.validation.Errors;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/tutorials")
public class TutorialController {

    @Autowired
    private TutorialRepository tutorialRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EnrollementRepository enrollementRepository;

    @GetMapping("/all")
    public String allTutorials(Model model){
        
        Iterable<Tutorial> tutorials = tutorialRepository.findAll();
        model.addAttribute("tutorials", tutorials);
        model.addAttribute("newEnrollement", new Enrollement());
        return "tutorials";
    }

    @GetMapping("/enrolled")
    public String enrolledTutorials(Model model){
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("enrolledTutorials", enrollementRepository.getEnrolledTutorials(
              (User) ob
        ));
        return "enrolled";
    }

    @PostMapping("/enrolled")
    public String unenrollTutorial(@RequestParam(name = "tutorialId") Long tutorialId, Model model){

        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Tutorial tutorial = tutorialRepository.findByTutorialId(tutorialId);
        Enrollement enrollement = enrollementRepository.getEnrollementFromClientAndTutorial(
                (User) ob, tutorial);
        enrollementRepository.delete(enrollement);
        model.addAttribute("enrolledTutorials", enrollementRepository.getEnrolledTutorials(
                (User) ob
        ));
        return "enrolled";
    }
	
	@GetMapping("/mytutorials")
    public String myTutorials(Model model){

        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("myTutorials",
                tutorialRepository.getInstructorTutorials((User) ob)
                );

        return "myTutorials";
    }

    @GetMapping("/create")
    public String createTutorial(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("newTutorial", new Tutorial());
        return "createTutorial";
    }

    @PostMapping("/create")
    public String processTutorial(@Valid Tutorial newTutorial, Errors errors,
                                  @RequestParam(required = false, name = "project_title")
                                          String projectTitle,
                                  @RequestParam(required = false, name = "project_statement")
                                  String projectStatement) {

        if (errors.hasErrors()) {
            return "createTutorial";
        }

        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ( ob != null){
            newTutorial.setInstructor((User) ob);
        }

        newTutorial.setModifedAt(LocalDateTime.now());

        if (projectTitle != null && projectStatement != null){
            log.info("Title "+projectTitle+" statement "+projectStatement);
            Project project = new Project();
            project.setTitle(projectTitle);
            project.setProblemStatement(projectStatement);
            projectRepository.save(project);
            newTutorial.setProject(project);
        }
        
//        log.info("Processing "+newTutorial);
        tutorialRepository.save(newTutorial);
        return "redirect:/";
    }

    @GetMapping("/detail/{tutorialId}")
    public String getTutorialDetailsById(@PathVariable("tutorialId") Long tutorialId, Model model){

        model.addAttribute("tutorial", tutorialRepository.findByTutorialId(tutorialId));
        return "tutorial_detail";
    }

    @PostMapping("/detail/{tutorialId}")
    public String submitProject(@PathVariable("tutorialId") Long tutorialId, Model model,
                                @RequestParam(name = "projectUrl") String projectUrl){
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Enrollement enrollement = enrollementRepository.getEnrollementFromClientAndTutorial(
                ((User) ob), tutorialRepository.findByTutorialId(tutorialId));
        log.info("enrollement "+enrollement);
        enrollement.setGithubLink(projectUrl);
        enrollementRepository.save(enrollement);
        model.addAttribute("tutorial", tutorialRepository.findByTutorialId(tutorialId));
        return "tutorial_detail";
    }
	

}
