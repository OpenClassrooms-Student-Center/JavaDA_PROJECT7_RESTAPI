package com.nnk.springboot.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.RequestedObjectNotFoundException;
import com.nnk.springboot.repositories.UserRepository;

import jakarta.transaction.Transactional;


/**
 * Responsible for managing user related operations.
 */
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor for UserService class.
     * @param userRepository userRepository for performing CRUD operations
     * @param user user object to be saved
     */
    public UserService(UserRepository userRepository, User user) {
        this.userRepository = userRepository;
        save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    /**
     * Encrypts the password and saves the user object.
     * @param user user object to be saved
     * @return saved user object
     */
    public User save(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Finds a user by their ID.
     * @param id user ID
     * @return user object
     * @throws RequestedObjectNotFoundException if user not found
     */
    public User findById(Integer id) throws RequestedObjectNotFoundException{
        return userRepository.findById(id).orElseThrow(()-> new RequestedObjectNotFoundException("User not found with id: " + id));
    }

    /**
     * Deletes a user by their ID.
     * @param user user ID
     */
    @Transactional
    public void delete(int user) {
        userRepository.deleteByuserid(user);
    }

    /**
     * Finds all users.
     * @return list of user objects
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

}


