package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();

     void saveUser (User user);

     Optional<User> getUserById (int id);

    void deleteUser(User user);
}
