package com.nnk.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.UserDetailsImpl;
import com.nnk.springboot.repositories.UserRepository;

/*
 * Implementation of UserDetailsService for Spring Security role based security
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	User user = userRepository.getUserByUsername(username);
	if (user == null) {
	    throw new UsernameNotFoundException("User not found");
	}
	return new UserDetailsImpl(user);
    }

}
