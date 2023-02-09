package com.nnk.springboot.service;

import com.nnk.springboot.model.User;
import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return new User(user.get());
        }
        throw new UsernameNotFoundException("User not found : " + username);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (userId != null && user.isPresent()) {
            log.info("User " + user.get().getFullname() + " find");
        }

        return user.get();
    }

    public boolean createUser(User user) {
        if (user != null && !userRepository.findByUsername(user.getUsername()).isPresent()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            log.info("New user with username : " + user.getUsername() + " and role " + user.getRole() + " has been created");
        }
        return true;
    }

    public boolean updateUser(Integer userId, User userUpdated) {
        Optional<User> user = userRepository.findById(userId);
        if (userId != null && user.isPresent()) {

            userUpdated.setId(userId);
            userUpdated.setPassword(passwordEncoder.encode(userUpdated.getPassword()));
            userRepository.save(userUpdated);

            log.info("User with username : " + userUpdated.getUsername() + " and role " + userUpdated.getRole() + " has been updated");
        }
        return true;
    }

    public boolean deleteUser(Integer userId){
        Optional<User> user = userRepository.findById(userId);
        if (userId != null && user.isPresent()) {
            userRepository.delete(user.get());
            log.info("User has been deleted");
        }
        return true;
    }
}
