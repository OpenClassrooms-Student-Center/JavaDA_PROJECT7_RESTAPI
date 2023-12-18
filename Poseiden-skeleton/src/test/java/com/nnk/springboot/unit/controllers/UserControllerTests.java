package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.config.CustomUserDetails;
import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;

import java.util.Collection;
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
    private UserService userService;
    
    @MockBean
    private BindingResult bindingResult;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        rating.setId(1);

        when(userService.findAll()).thenReturn(userList);
        when(userService.findById(any(Integer.class))).thenReturn(userOptional);
        when(userService.findByUsername(any(String.class))).thenReturn(user);
        when(bindingResult.hasErrors()).thenReturn(false);
    }

    @Test
    public void ContextLoads() {}

    @Nested
    public class HomeTests {
        @Test
        public void homeTest () {
            assertEquals("user/list", userController.home(model));
            verify(userService, Mockito.times(1)).findAll();
        }
    }
    
    @Nested
    public class AddUserTests {
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
    public class ValidateTests {
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
    public class ShowUpdateFormTests {
        @Test
        public void showUpdateFormTest () {
            assertEquals("user/update", userController.showUpdateForm(1, model, authentication));
            verify(userService, Mockito.times(1)).findById(any(Integer.class));
        }

        @Test
        public void showUpdateFormTestIfUserNotFound () {
            when(userService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertThrows(IllegalArgumentException.class, () -> userController.showUpdateForm(user.getId(), model, authentication));
            verify(userService, Mockito.times(1)).findById(null);
        }

        @Test
        public void showUpdateFormTestIfRequestByAdmin () {
            user.setRole("ADMIN");
            assertEquals("user/update", userController.showUpdateForm(1, model, authentication));
            verify(userService, Mockito.times(1)).findById(any(Integer.class));
        }

        @Test
        public void showUpdateFormTestIfRequestByAnotherUser () {

            authentication = new Authentication() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return new CustomUserDetails(user).getAuthorities();
                }

                @Override
                public Object getCredentials() {
                    return null;
                }

                @Override
                public Object getDetails() {
                    return null;
                }

                @Override
                public Object getPrincipal() {
                    return null;
                }

                @Override
                public boolean isAuthenticated() {
                    return false;
                }

                @Override
                public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

                }

                @Override
                public String getName() {
                    return "notTheRightUsername";
                }
            };
            assertEquals("error/403", userController.showUpdateForm(1, model, authentication));
            verify(userService, Mockito.times(1)).findById(any(Integer.class));
        }
    }

    @Nested
    public class UpdateUserTests {
        @Test
        public void updateUserTest () {
            assertEquals("redirect:/user/list", userController.updateUser(1, user, bindingResult, model, authentication));
            verify(userService, Mockito.times(1)).findById(any(Integer.class));
            verify(userService, Mockito.times(1)).save(any(User.class));
        }

        @Test
        public void updateUserTestIfInvalidUser () {
            when(bindingResult.hasErrors()).thenReturn(true);
            assertEquals("redirect:/user/update/{id}", userController.updateUser(1, user, bindingResult, model, authentication));
            verify(userService, Mockito.times(0)).findById(any(Integer.class));
            verify(userService, Mockito.times(0)).save(any(User.class));
        }

        @Test
        public void updateUserTestIfInvalidPassword () {
            user.setPassword(passwordIncorrect);
            assertEquals("redirect:/user/update/{id}", userController.updateUser(1, user, bindingResult, model, authentication));
            verify(userService, Mockito.times(0)).findById(any(Integer.class));
            verify(userService, Mockito.times(0)).save(any(User.class));
        }

        @Test
        public void updateUserTestIfUserNotInDB () {
            when(userService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertEquals("redirect:/user/update/{id}", userController.updateUser(1, user, bindingResult, model, authentication));
            verify(userService, Mockito.times(1)).findById(any(Integer.class));
            verify(userService, Mockito.times(0)).save(any(User.class));
        }

        @Test
        public void updateUserTestIfRequestByAdmin () {
            user.setRole("ADMIN");
            assertEquals("redirect:/user/list", userController.updateUser(1, user, bindingResult, model, authentication));
            verify(userService, Mockito.times(1)).findById(any(Integer.class));
            verify(userService, Mockito.times(1)).save(any(User.class));
        }

        @Test
        public void updateUserTestIfRequestByAnotherUser () {

            authentication = new Authentication() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return new CustomUserDetails(user).getAuthorities();
                }

                @Override
                public Object getCredentials() {
                    return null;
                }

                @Override
                public Object getDetails() {
                    return null;
                }

                @Override
                public Object getPrincipal() {
                    return null;
                }

                @Override
                public boolean isAuthenticated() {
                    return false;
                }

                @Override
                public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

                }

                @Override
                public String getName() {
                    return "notTheRightUsername";
                }
            };
            assertEquals("error/403", userController.updateUser(1, user, bindingResult, model, authentication));
            verify(userService, Mockito.times(1)).findById(any(Integer.class));
            verify(userService, Mockito.times(0)).save(any(User.class));
        }
    }

    @Nested
    public class DeleteUserTests {
        @Test
        public void deleteUserTest () {
            assertEquals("redirect:/user/list", userController.deleteUser(1, model));
            verify(userService, Mockito.times(1)).deleteById(any(Integer.class));
        }
    }
}
