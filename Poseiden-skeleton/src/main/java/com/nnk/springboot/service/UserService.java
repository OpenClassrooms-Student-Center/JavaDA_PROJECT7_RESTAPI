package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findAll();

    User findById(Integer id);

    User create(User user);

    User update(Integer id, User user);

    void delete(Integer id);
}
