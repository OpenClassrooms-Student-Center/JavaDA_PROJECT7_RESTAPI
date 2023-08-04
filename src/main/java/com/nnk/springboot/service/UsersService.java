package com.nnk.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Users;
import com.nnk.springboot.repositories.UsersRepository;

@Service
@Component
public class UsersService {

    @Autowired
    private UsersRepository userRepository;

    public UsersService() {
    }

    public UsersService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public Users findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public Users getUser(String userName) {
        List<Users> usersList = findAll();
        for (Users users : usersList) {
            if (users.getUsername().equals(userName)) {
                return users;
            }
        }
        return null;
    }

    public Users save(Users users) {
        return userRepository.save(users);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public Users update(Users users) {
        return userRepository.save(users);
    }

}