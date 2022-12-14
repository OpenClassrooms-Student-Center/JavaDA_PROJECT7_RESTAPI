package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


public class UserServiceImpl implements IUserService {

    private static final Logger logger = LogManager.getLogger("UserServiceImpl");

    private UserRepository userRepository;


    @Override
    public List<User> findAll() {

        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) throws DataNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("No User with id " + id + "  found "));
    }

    @Override
    public void save(User user) {
        userRepository.save(user);

    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
