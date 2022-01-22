package com.groupproject.softwaretu.tutorial;

import com.groupproject.softwaretu.security.User;
import com.groupproject.softwaretu.tutorial.Tutorial;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;


public interface TutorialRepository extends CrudRepository<Tutorial, Long> {
//     Tutorial findByUsername(Long id);

    Tutorial findByTutorialId(Long Id);
    // public User findByUserEmail(String email);

    @Query("SELECT t FROM Tutorial t WHERE t.instructor = ?#{[0]}")
    Collection<Tutorial> getInstructorTutorials(User instructor);
}

