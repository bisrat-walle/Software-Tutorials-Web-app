package com.groupproject.softwaretu.enrollement;


import com.groupproject.softwaretu.security.User;
import com.groupproject.softwaretu.tutorial.Tutorial;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;


public interface EnrollementRepository extends CrudRepository<Enrollement, Long> {
    @Query("SELECT e FROM Enrollement e WHERE e.client = ?#{[0]} and e.tutorial = ?#{[1]}")
    Enrollement getEnrollementFromClientAndTutorial(User client, Tutorial tutorial);

    @Query("SELECT e.tutorial FROM Enrollement e WHERE e.client = ?#{[0]}")
    Collection<Tutorial> getEnrolledTutorials(User client);

    @Query("SELECT e.githubLink FROM Enrollement e WHERE e.client = ?#{[0]} and e.tutorial = ?#{[1]}")
    String getGithubLinkFromClientAndTutorial(User client, Tutorial tutorial);

    @Query("SELECT e FROM Enrollement e WHERE e.tutorial = ?#{[0]}")
    Collection<Enrollement> getEnrollementFromTutorial(Tutorial tutorial);
}
