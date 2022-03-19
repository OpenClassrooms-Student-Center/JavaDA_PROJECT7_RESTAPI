package com.nnk.springboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public Iterable<User> findAllUsers() {
		log.info("All User retrieved from database");
		return userRepository.findAll();		
	}
	
	public Optional<User> findUserById(Integer id) {
		log.info("User with id: " + id + " retrieved from database");
		return userRepository.findById(id);
	}
	
	public User saveUser(User user) {
		log.info("User: " + user.toString() + " saved in database");
		return userRepository.save(user);
	}
	
	public void deleteUser(User user) {
		log.info("User: " + user.toString() + " deleted in database");
		userRepository.delete(user);;
	}
}
