package com.groupproject.softwaretu.enrollement;

import com.groupproject.softwaretu.enrollement.EnrollementRepository;
import com.groupproject.softwaretu.security.User;
import com.groupproject.softwaretu.tutorial.Tutorial;
import com.groupproject.softwaretu.tutorial.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EnrollementService {
    @Autowired
    EnrollementRepository enrollementRepository;

    @Autowired
    TutorialRepository tutorialRepository;

    public boolean check(Tutorial tutorial) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return enrollementRepository.getEnrollementFromClientAndTutorial(user, tutorial) != null;
    }

    public boolean checkGithubLink(Tutorial tutorial) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return enrollementRepository.getGithubLinkFromClientAndTutorial(user, tutorial) == null;
    }

    public boolean checkTutorialInstructor(Tutorial tutorial) {
        User instructor = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return tutorialRepository.getInstructor(tutorial).equals(instructor);
    }
}
