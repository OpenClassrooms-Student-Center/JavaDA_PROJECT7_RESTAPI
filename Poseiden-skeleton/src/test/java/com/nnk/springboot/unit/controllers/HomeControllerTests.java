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
    public class HomeTests {
        @Test
        public void homeTestIfAdmin () {
            user.setRole("ADMIN");
            assertEquals("redirect:/home/admin", homeController.home(model, request, authentication));
        }

        @Test
        public void homeTestIfUser () {
            assertEquals("redirect:/home/user", homeController.home(model, request, authentication));
        }

        @Test
        public void homeTestIfNoRole () {
            user.setRole("");
            assertEquals("error/403", homeController.home(model, request, authentication));
        }
    }

    @Nested
    public class AdminHomeTests {
        @Test
        public void adminHomeTest () {
            assertEquals("redirect:/user/list", homeController.adminHome(model));
        }
    }

    @Nested
    public class UserHomeTests {
        @Test
        public void userHomeTest () {
            assertEquals("redirect:/bidList/list", homeController.userHome(model));
        }
    }
}
