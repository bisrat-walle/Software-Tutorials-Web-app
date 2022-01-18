package com.groupproject.softwaretu;

import org.springframework.data.repository.CrudRepository;

import com.groupproject.softwaretu.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}