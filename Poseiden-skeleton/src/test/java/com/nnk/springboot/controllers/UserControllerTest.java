package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Test
    void home() {
    }

    @Test
    void addUser() {
    }

    @Test
    void validate() {
    }

    @Test
    void showUpdateForm() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }
}