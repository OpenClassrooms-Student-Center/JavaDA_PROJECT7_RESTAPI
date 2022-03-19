package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer> {

}
