package com.nnk.springboot.unit.services;

import com.nnk.springboot.config.security.JwtTokenUtil;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import com.nnk.springboot.services.impl.AuthenticationUserDetailService;
import com.nnk.springboot.services.impl.JwtServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthentificationUserDetailServiceTest {
    UserService userService;
    AuthenticationUserDetailService service;

    @BeforeEach
    void init(){
        userService = mock(UserService.class);
        service = new AuthenticationUserDetailService(userService);
    }

    @Test
    public void shouldLoadByUsername() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password" +
                "");
        user.setRole("role");
        when(userService.findUserByUsername(user.getUsername())).thenReturn(user);
        UserDetails userDetails = service.loadUserByUsername("username");
        Assert.assertEquals(userDetails.getUsername(), user.getUsername());
    }

}
