package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = LoginController.class)
public class LoginControllerTests extends TestVariables {

    @Autowired
    LoginController loginController;
    
    @MockBean
    UserService userService;

    @MockBean
    HttpServletRequest request;

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
        public void errorTestIf403 () {
            when(request.getAttribute(any(String.class))).thenReturn(HttpStatus.FORBIDDEN.value());
            String errorMessage = "You are not authorized for the requested data.";
            mav.addObject("errorMsg", errorMessage);
            mav.setViewName("403");
            assertEquals(mav.toString(), loginController.error(request).toString());
        }
        @Test
        public void errorTestIf404 () {
            when(request.getAttribute(any(String.class))).thenReturn(HttpStatus.NOT_FOUND.value());
            String errorMessage = "The requested resource cannot be found.";
            mav.addObject("errorMsg", errorMessage);
            mav.setViewName("404");
            assertEquals(mav.toString(), loginController.error(request).toString());
        }
        @Test
        public void errorTestIfOtherStatus () {
            when(request.getAttribute(any(String.class))).thenReturn(HttpStatus.CONFLICT.value());
            String errorMessage = "Something went wrong.";
            mav.addObject("errorMsg", errorMessage);
            mav.setViewName("error");
            assertEquals(mav.toString(), loginController.error(request).toString());
        }
        @Test
        public void errorTestIfNoStatus () {
            when(request.getAttribute(any(String.class))).thenReturn(null);
            String errorMessage = "Something went wrong.";
            mav.addObject("errorMsg", errorMessage);
            mav.setViewName("error");
            assertEquals(mav.toString(), loginController.error(request).toString());
        }
    }
}
