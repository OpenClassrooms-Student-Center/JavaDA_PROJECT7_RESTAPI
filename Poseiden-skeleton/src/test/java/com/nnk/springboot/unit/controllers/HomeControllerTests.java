package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.controllers.HomeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = HomeController.class)
public class HomeControllerTests extends TestVariables {

    @Autowired
    HomeController homeController;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
    }

    @Test
    public void ContextLoads() {}

    @Nested
    public class HomeTests
    {
        @Test
        public void homeTest () {
            assertEquals("home", homeController.home(model));
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
