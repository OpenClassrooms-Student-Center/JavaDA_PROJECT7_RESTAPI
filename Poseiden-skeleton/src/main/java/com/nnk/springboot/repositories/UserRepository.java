package com.nnk.springboot.repositories;

import com.nnk.springboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {


    User findUserByUsername(String username);

}
