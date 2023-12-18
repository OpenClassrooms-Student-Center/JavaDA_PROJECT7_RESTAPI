package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        return userRepository.findById(id);
    }

    public User findByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("username is null");
        }
        return userRepository.findByUsername(username);
    }

    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.deleteById(id);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
