package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void saveUser (User user){
        userRepository.save(user);
    }

    public Optional<User> getUserById (int id) {
      return userRepository.findById(id);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}