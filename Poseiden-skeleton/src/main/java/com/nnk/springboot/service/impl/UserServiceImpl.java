package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;
import com.nnk.springboot.util.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Service class that handles all User related business logic.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository the user repository
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        log.info("Retrieving all Userss");
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) {
        log.info("Retrieving User with id {}", id);
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new NotFoundException("User not found with id " + id));
    }

    @Override
    public User create(User user) {
        log.info("Creating new User");
        return userRepository.save(user);
    }

    @Override
    public User update(Integer id, User user) {
        log.info("Updating User with id {}", id);
        User userToUpdate = findById(id);
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setFullname(user.getFullname());
        userToUpdate.setRole(user.getRole());
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting User with id {}", id);
        User user = findById(id);
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException(userName));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }
}
