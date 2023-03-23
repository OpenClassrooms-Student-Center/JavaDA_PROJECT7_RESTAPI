package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.UserPrincipal;
import com.nnk.springboot.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    static final Logger log = LogManager.getLogger();
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user= userRepository.findUserByUsername(username);

        if (user == null){
            log.info("user not found");
            throw  new UsernameNotFoundException("Username not found"+username);
        }
        log.info("user found");
        return new UserPrincipal(user) ;
    }
}
