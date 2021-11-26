package com.nnk.springboot.unit.services;

import com.nnk.springboot.config.security.JwtTokenUtil;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.BidRepository;
import com.nnk.springboot.services.impl.BidServiceImpl;
import com.nnk.springboot.services.impl.JwtServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtServiceImplTest {
    JwtTokenUtil jwtUtil;
    AuthenticationManager authenticationManager;
    UserDetailsService userDetailsService;
    JwtServiceImpl service;

    @BeforeEach
    void init(){
        jwtUtil = mock(JwtTokenUtil.class);
        authenticationManager = mock(AuthenticationManager.class);
        userDetailsService = mock(UserDetailsService.class);
        service = new JwtServiceImpl(jwtUtil, authenticationManager, userDetailsService);
    }

    @Test
    public void shouldAuthenticate() throws Exception {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(userDetailsService.loadUserByUsername("username")).thenReturn(mock(UserDetails.class));
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("token");
        String token = service.authenticate(user);
        Assert.assertEquals(token,"token");
    }
}

