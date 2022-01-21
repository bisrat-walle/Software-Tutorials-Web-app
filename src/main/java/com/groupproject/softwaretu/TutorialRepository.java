package com.groupproject.softwaretu;

import org.springframework.data.repository.CrudRepository;

public interface TutorialRepository extends CrudRepository<Tutorial, Long> {
    // public Tutorial findByUsername(Long id);
    // public User findByUserEmail(String email);
}

