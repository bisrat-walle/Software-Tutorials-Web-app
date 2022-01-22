package com.groupproject.softwaretu.security;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(String username);
    // public User findByUserEmail(String email);
}

