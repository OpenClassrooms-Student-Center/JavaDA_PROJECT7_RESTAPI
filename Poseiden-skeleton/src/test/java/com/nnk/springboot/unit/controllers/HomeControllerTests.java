package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.controllers.HomeController;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = HomeController.class)
public class HomeControllerTests extends TestVariables {

    @Autowired
    HomeController homeController;

    @MockBean
    UserService userService;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        when(userService.findByUsername(any(String.class))).thenReturn(user);
    }

    @Test
    public void ContextLoads() {}

    @Nested
    public class HomeTests
    {
        @Test
        public void homeTest () {
            assertEquals("home", homeController.home(model, request));
        }
    }

    @Nested
    public class AdminHomeTests
    {
        @Test
        public void adminHomeTest () {
            assertEquals("redirect:/bidList/list", homeController.adminHome(model));
        }
    }
}
