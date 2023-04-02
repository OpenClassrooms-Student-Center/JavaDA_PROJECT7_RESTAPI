package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    static final Logger log = LogManager.getLogger();

    private UserRepository userRepository;
    private PasswordEncoder passWordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passWordEncoder){
        this.userRepository=userRepository;
        this.passWordEncoder=passWordEncoder;
    }
    public User registerNewUser(String username, String password, String fullname, String role){

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFullname(fullname);
        user.setRole(role);
        userRepository.save(user);
        return user;
    }
    public void deleteUserByUsername(String username){
        User user = userRepository.findUserByUsername(username);
        userRepository.delete(user);
    }
    public String userNameOfCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //String userNameOfCurrentUser = auth.getName();
        return auth.getName();
    }

}
