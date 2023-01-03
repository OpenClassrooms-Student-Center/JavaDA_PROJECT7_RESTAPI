package com.nnk.springboot.service;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.beans.BeanProperty;


public interface IUserDetailService extends UserDetailsService {

    @BeanProperty
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
