package com.groupproject.softwaretu.tutorial;

import com.groupproject.softwaretu.enrollement.Enrollement;
import com.groupproject.softwaretu.enrollement.EnrollementRepository;
import com.groupproject.softwaretu.project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorialService {
    @Autowired
    TutorialRepository tutorialRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    EnrollementRepository enrollementRepository;

    void deleteTutorialCascadeProject(Long id){
        Tutorial tutorial = tutorialRepository.findByTutorialId(id);
        if (tutorial.getProject() != null){
            projectRepository.delete(tutorial.getProject());
        }

        enrollementRepository.getEnrollementFromTutorial(tutorial).forEach(
                i -> {

                    
                    enrollementRepository.delete(i);
                }
        );

        tutorialRepository.deleteByTutorialId(id);
    }
}
