package com.nnk.springboot.services;

import java.util.Optional;

import com.nnk.springboot.domain.User;

public interface IUserService {
    public Iterable<User> getUsers();

    public Optional<User> getUserById(Integer id);

    public User saveUser(User user);

    public void deleteUserById(Integer id);
}
