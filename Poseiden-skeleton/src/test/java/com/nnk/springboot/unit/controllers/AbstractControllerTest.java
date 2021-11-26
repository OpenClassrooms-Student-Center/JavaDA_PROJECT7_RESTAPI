package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.config.security.JwtTokenUtil;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;

public abstract class AbstractControllerTest {

    @MockBean
    UserDetailsService userDetailsService;
    @MockBean
    JwtTokenUtil jwtTokenUtil;

}
