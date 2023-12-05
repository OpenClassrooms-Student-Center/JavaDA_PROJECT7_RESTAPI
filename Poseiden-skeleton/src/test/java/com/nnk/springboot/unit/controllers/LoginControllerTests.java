package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = LoginController.class)
public class LoginControllerTests extends TestVariables {

    @Autowired
    LoginController loginController;
    
    @MockBean
    UserService userService;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        when(userService.findAll()).thenReturn(userList);
    }

    @Test
    public void ContextLoads() {}

    @Nested
    public class LoginTests {
        @Test
        public void loginTest () {
            mav.setViewName("login");
            assertEquals(mav.toString(), loginController.login().toString());
        }
    }

    @Nested
    public class GetAllUserArticlesTests {
        @Test
        public void getAllUserArticlesTest () {
            mav.addObject("users", userList);
            mav.setViewName("user/list");
            assertEquals(mav.toString(), loginController.getAllUserArticles().toString());
        }
    }
    
    @Nested
    public class ErrorTests {
        @Test
        public void errorTest () {
            String errorMessage= "You are not authorized for the requested data.";
            mav.addObject("errorMsg", errorMessage);
            mav.setViewName("403");
            assertEquals(mav.toString(), loginController.error().toString());
        }
    }
}
