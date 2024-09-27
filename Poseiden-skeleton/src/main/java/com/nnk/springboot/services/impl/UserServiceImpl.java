package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     *{@inheritDoc}
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     *{@inheritDoc}
     */
    public boolean checkIfUserExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     *{@inheritDoc}
     */
    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }
}
