package com.groupproject.softwaretu.tutorial;

import com.groupproject.softwaretu.security.User;
import com.groupproject.softwaretu.tutorial.Tutorial;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Collection;


public interface TutorialRepository extends CrudRepository<Tutorial, Long> {
//     Tutorial findByUsername(Long id);

    Tutorial findByTutorialId(Long Id);
    // public User findByUserEmail(String email);

    @Query("SELECT t FROM Tutorial t WHERE t.instructor = ?#{[0]}")
    Collection<Tutorial> getInstructorTutorials(User instructor);

    @Query("SELECT t.instructor FROM Tutorial t WHERE t = ?#{[0]}")
    User getInstructor(Tutorial tutorial);

    @Modifying
    @Transactional
    @Query("DELETE FROM Tutorial t WHERE t.tutorialId = ?#{[0]}")
    void deleteByTutorialId(Long tutorialId);



}

