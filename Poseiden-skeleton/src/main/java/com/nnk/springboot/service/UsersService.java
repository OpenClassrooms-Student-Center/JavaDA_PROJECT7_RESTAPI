package com.nnk.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;

    public UsersService() {
    }

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUser(String userName) {
        List<User> usersList = findAll();
        for (User users : usersList) {
            if (users.getUsername().equals(userName)) {
                return users;
            }
        }
        return null;
    }

    public User save(User users) {
        return userRepository.save(users);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public User update(User users) {
        return userRepository.save(users);
    }

}