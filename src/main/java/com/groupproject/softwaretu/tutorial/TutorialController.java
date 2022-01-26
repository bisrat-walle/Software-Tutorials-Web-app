package com.groupproject.softwaretu.tutorial;

import com.groupproject.softwaretu.enrollement.Enrollement;
import com.groupproject.softwaretu.enrollement.EnrollementRepository;
import com.groupproject.softwaretu.enrollement.EnrollementService;
import com.groupproject.softwaretu.project.Project;
import com.groupproject.softwaretu.project.ProjectRepository;
import com.groupproject.softwaretu.security.UserRepository;
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
    public EnrollementService enrollementChecker;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TutorialService tutorialService;

    @Autowired
    private EnrollementRepository enrollementRepository;
	
	@Autowired
    private UserRepository userRepository ;

    @GetMapping("/all")
    public String allTutorials(Model model){

        Boolean loggedIn = true;
        Boolean isInstructor = true;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addAttribute("user", user);
            if (!user.getRole().equals("INSTRUCTOR")){
                isInstructor = false;
            }
        } catch (ClassCastException e){
            loggedIn = false;
        }
        model.addAttribute("loggedIn", loggedIn);
        model.addAttribute("isInstructor", isInstructor);
        
        Iterable<Tutorial> tutorials = tutorialRepository.findAll();
        model.addAttribute("tutorials", tutorials);
        model.addAttribute("newEnrollement", new Enrollement());
        model.addAttribute("enrollementChecker", enrollementChecker);
        model.addAttribute("tutorialRepo", tutorialRepository);
        return "tutorials";
    }

    @GetMapping("/enrolled")
    public String enrolledTutorials(Model model){

        Boolean loggedIn = true;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addAttribute("user", user);
        } catch (ClassCastException e){
            loggedIn = false;
        }
        model.addAttribute("loggedIn", loggedIn);

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
        log.info("=============================================================================It is going to be deleted");
        enrollementRepository.delete(enrollement);
        model.addAttribute("enrolledTutorials", enrollementRepository.getEnrolledTutorials(
                (User) ob
        ));

        Boolean loggedIn = true;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", user);
        } catch (ClassCastException e){
            loggedIn = false;
        }
        model.addAttribute("loggedIn", loggedIn);
        return "enrolled";
    }

	@GetMapping("/mytutorials")
    public String myTutorials(Model model){

        Boolean loggedIn = true;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.addAttribute("user", user);
        } catch (ClassCastException e){
            loggedIn = false;
        }
        model.addAttribute("loggedIn", loggedIn);

        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("myTutorials",
                tutorialRepository.getInstructorTutorials((User) ob)
                );

        return "myTutorials";
    }
	
	

    @GetMapping("/create")
    public String createTutorial(Model model){

        Boolean loggedIn = true;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e){
            loggedIn = false;
        }
        model.addAttribute("loggedIn", loggedIn);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("tutorial", new Tutorial());
        return "createTutorial";
    }

    @PostMapping("/create")
    public String processTutorial(@Valid Tutorial tutorial, Errors errors,
                                  @RequestParam(required = false, name = "project_title")
                                          String projectTitle,
                                  @RequestParam(required = false, name = "project_statement")
                                  String projectStatement,
                                  Model model) {

        Boolean loggedIn = true;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e){
            loggedIn = false;
        }
        model.addAttribute("loggedIn", loggedIn);




        if (errors.hasErrors()) {
            log.info("Error creating tutorial");
            return "createTutorial";
        }

        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ( ob != null){
            tutorial.setInstructor((User) ob);
        }

        tutorial.setModifiedAt(LocalDateTime.now());

        if (projectTitle != null
                && projectTitle.length() >= 5
                && projectStatement != null
                && projectStatement.length() >= 5){
            log.info("Title "+projectTitle+" statement "+projectStatement);
            Project project = new Project();
            project.setTitle(projectTitle);
            project.setProblemStatement(projectStatement);
            projectRepository.save(project);
            tutorial.setProject(project);
        }
        
//        log.info("Processing "+tutorial);
        tutorialRepository.save(tutorial);
        return "redirect:/tutorials/mytutorials";
    }

    @GetMapping("/edit")
    public String editTutorial(@RequestParam(name = "tutorialId") Long id, Model model){
        Tutorial newTutorial = tutorialRepository.findByTutorialId(id);
        model.addAttribute("newTutorial", newTutorial);

        Boolean loggedIn = true;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e){
            loggedIn = false;
        }
        model.addAttribute("loggedIn", loggedIn);
        return "editTutorial";
    }

    @PostMapping("/edit")
    public String editTutorial(@Valid Tutorial newTutorial, Errors errors,
                                  @RequestParam(required = false, name = "project_title")
                                          String projectTitle,
                                  @RequestParam(required = false, name = "project_statement")
                                          String projectStatement,
                                  Model model) {

        Boolean loggedIn = true;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e){
            loggedIn = false;
        }
        model.addAttribute("loggedIn", loggedIn);




        if (errors.hasErrors()) {
            model.addAttribute("newTutorial", newTutorial);
            log.info("Error creating tutorial");
            return "redirect:/tutorials/create?error";
        }

        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ( ob != null){
            newTutorial.setInstructor((User) ob);
        }

        newTutorial.setModifiedAt(LocalDateTime.now());

        if (projectTitle != null
                && projectTitle.length() >= 5
                && projectStatement != null
                && projectStatement.length() >= 5){
            log.info("Title "+projectTitle+" statement "+projectStatement);
            Project project = new Project();
            project.setTitle(projectTitle);
            project.setProblemStatement(projectStatement);
            projectRepository.save(project);
            newTutorial.setProject(project);
        }

//        log.info("Processing "+newTutorial);
        tutorialRepository.save(newTutorial);
        return "redirect:/tutorials/mytutorials";
    }
	
//	@PostMapping("/delete")
//    public String deleteTutorial(@RequestParam(name = "tutorialId") Long id, Model model){
//
//        log.info("ID"+id);
//        Tutorial tutorial = tutorialRepository.findByTutorialId(id);
//
//		tutorialRepository.delete(tutorial);
//
//        boolean loggedIn = true;
//        try {
//            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        } catch (ClassCastException e){
//            loggedIn = false;
//        }
//
//		model.addAttribute("loggedIn", loggedIn);
//		model.addAttribute("totalEnrollement", enrollementRepository.findAll());
//		model.addAttribute("totalClient", userRepository.getUserOfType("CLIENT"));
//		model.addAttribute("totalInstructor", userRepository.getUserOfType("INSTRUCTOR"));
//		model.addAttribute("users", userRepository.findAll());
//		model.addAttribute("totalProject", projectRepository.findAll());
//		model.addAttribute("totalTutorial", tutorialRepository.findAll());
//
//        return "redirect:/";
//    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam(name="tutorialId") Long id) {

        tutorialService.deleteTutorialCascadeProject(id);
        return "redirect:/admin";
    }

    @GetMapping("/detail/{tutorialId}")
    public String getTutorialDetailsById(@PathVariable("tutorialId") Long tutorialId, Model model){

        Boolean loggedIn = true;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e){
            loggedIn = false;
        }
        model.addAttribute("loggedIn", loggedIn);

        model.addAttribute("tutorial", tutorialRepository.findByTutorialId(tutorialId));
        model.addAttribute("enrollementService", enrollementChecker);
        return "tutorial_detail";
    }

    @PostMapping("/detail/{tutorialId}")
    public String submitProject(@PathVariable("tutorialId") Long tutorialId, Model model,
                                @RequestParam(name = "projectUrl") String projectUrl){
        Object ob = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Boolean loggedIn = true;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e){
            loggedIn = false;
        }

        model.addAttribute("loggedIn", loggedIn);


        Enrollement enrollement = enrollementRepository.getEnrollementFromClientAndTutorial(
                ((User) ob), tutorialRepository.findByTutorialId(tutorialId));
        log.info("enrollement "+enrollement);
        enrollement.setGithubLink(projectUrl);
        enrollementRepository.save(enrollement);
        model.addAttribute("tutorial", tutorialRepository.findByTutorialId(tutorialId));
        model.addAttribute("enrollementService", enrollementChecker);
        return "tutorial_detail";
    }



}


