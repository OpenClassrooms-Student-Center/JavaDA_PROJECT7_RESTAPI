package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserController.class)
public class UserControllerTests extends TestVariables {
    
    @Autowired
    UserController userController;
    
    @MockBean
    private UserRepository userService;
    
    @MockBean
    private BindingResult bindingResult;
    
    private Model model;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        rating.setId(1);
        model = new Model() {
            @Override
            public Model addAttribute(String attributeName, Object attributeValue) {
                return null;
            }

            @Override
            public Model addAttribute(Object attributeValue) {
                return null;
            }

            @Override
            public Model addAllAttributes(Collection<?> attributeValues) {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public Model mergeAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public boolean containsAttribute(String attributeName) {
                return false;
            }

            @Override
            public Object getAttribute(String attributeName) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        };

        when(userService.findAll()).thenReturn(userList);
        when(userService.findById(any(Integer.class))).thenReturn(userOptional);
        when(bindingResult.hasErrors()).thenReturn(false);
    }

    @Test
    public void ContextLoads() {}

    @Nested
    public class homeTests
    {
        @Test
        public void homeTest () {
            assertEquals("user/list", userController.home(model));
            verify(userService, Mockito.times(1)).findAll();
        }
    }
    
    @Nested
    public class addUserTests
    {
        @Test
        public void addUserTest () {
            assertEquals("user/add", userController.addUser(user));
        }
        @Test
        public void addUserTestIfEmpty () {
            assertEquals("user/add", userController.addUser(new User()));
        }
        @Test
        public void addUserTestIfNull () {
            assertEquals("user/add", userController.addUser(null));
        }
    }
    
    @Nested
    public class validateTests
    {
        @Test
        public void validateTest () {
            assertEquals("redirect:/user/list", userController.validate(user, bindingResult, model));
            verify(userService, Mockito.times(1)).save(any(User.class));
            verify(userService, Mockito.times(1)).findAll();
        }

        @Test
        public void validateTestIfIncorrectUser () {
            when(bindingResult.hasErrors()).thenReturn(true);
            assertEquals("user/add", userController.validate(user, bindingResult, model));
            verify(userService, Mockito.times(0)).save(any(User.class));
            verify(userService, Mockito.times(0)).findAll();
        }

        @Test
        public void validateTestIfIncorrectPassword () {
            user.setPassword(passwordIncorrect);
            assertEquals("user/add", userController.validate(user, bindingResult, model));
            verify(userService, Mockito.times(0)).save(any(User.class));
            verify(userService, Mockito.times(0)).findAll();
        }
    }

    @Nested
    public class showUpdateFormTests
    {
        @Test
        public void showUpdateFormTest () {
            assertEquals("user/update", userController.showUpdateForm(1, model));
            verify(userService, Mockito.times(1)).findById(any(Integer.class));
        }

        @Test
        public void showUpdateFormTestIfUserNotFound () {
            when(userService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertThrows(IllegalArgumentException.class, () -> userController.showUpdateForm(user.getId(), model));
            verify(userService, Mockito.times(1)).findById(null);
        }
    }

    @Nested
    public class updateUserTests
    {
        @Test
        public void updateUserTest () {
            assertEquals("redirect:/user/list", userController.updateUser(1, user, bindingResult, model));
            verify(userService, Mockito.times(1)).findById(any(Integer.class));
            verify(userService, Mockito.times(1)).save(any(User.class));
        }

        @Test
        public void updateUserTestIfInvalidUser () {
            when(bindingResult.hasErrors()).thenReturn(true);
            assertEquals("user/update", userController.updateUser(1, user, bindingResult, model));
            verify(userService, Mockito.times(0)).findById(any(Integer.class));
            verify(userService, Mockito.times(0)).save(any(User.class));
        }

        @Test
        public void updateUserTestIfInvalidPassword () {
            user.setPassword(passwordIncorrect);
            assertEquals("user/update", userController.updateUser(1, user, bindingResult, model));
            verify(userService, Mockito.times(0)).findById(any(Integer.class));
            verify(userService, Mockito.times(0)).save(any(User.class));
        }

        @Test
        public void updateUserTestIfUserNotInDB () {
            when(userService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertEquals("user/update", userController.updateUser(1, user, bindingResult, model));
            verify(userService, Mockito.times(1)).findById(any(Integer.class));
            verify(userService, Mockito.times(0)).save(any(User.class));
        }
    }

    @Nested
    public class deleteUserTests
    {
        @Test
        public void deleteUserTest () {
            assertEquals("redirect:/user/list", userController.deleteUser(1, model));
            verify(userService, Mockito.times(1)).findById(any(Integer.class));
            verify(userService, Mockito.times(1)).delete(any(User.class));
            verify(userService, Mockito.times(1)).findAll();
        }

        @Test
        public void deleteUserTestIfUserNotFound () {
            when(userService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertThrows(IllegalArgumentException.class, () -> userController.deleteUser(user.getId(), model));
            verify(userService, Mockito.times(1)).findById(null);
            verify(userService, Mockito.times(0)).delete(any(User.class));
            verify(userService, Mockito.times(0)).findAll();
        }
    }
}
