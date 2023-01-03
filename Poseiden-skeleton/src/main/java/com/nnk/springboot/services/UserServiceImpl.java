package com.nnk.springboot.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

/*
 * Service for handling User related operations
 */
@Service
public class UserServiceImpl implements IUserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
	this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Iterable<User> getUsers() {
	return userRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<User> getUserById(Integer id) {
	return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User saveUser(User user) {
	// Assigning by default role "User" (id = 1)
	// Role defaultRole = roleService.getRoleById(1);
	// user.addRole(defaultRole);
	return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Integer id) {
	userRepository.deleteById(id);
    }

}
