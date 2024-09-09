package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = User.class, idClass = Integer.class)
public interface UserRepository{
    Optional<User> findByUsername(String username);
    List<User> findAll();
    Optional<User> findById(Integer id);
    void deleteByuserid(int user);
    User save(User user);
}
