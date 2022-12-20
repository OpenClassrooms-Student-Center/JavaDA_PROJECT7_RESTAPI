package com.nnk.springboot.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserDetailService extends UserDetailsService {


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
