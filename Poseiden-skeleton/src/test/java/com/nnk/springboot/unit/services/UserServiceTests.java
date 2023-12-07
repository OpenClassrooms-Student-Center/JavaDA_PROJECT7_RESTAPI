package com.nnk.springboot.unit.services;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserService.class)
public class UserServiceTests extends TestVariables {

    @Autowired
    UserService userService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        user.setId(1);
        when(userRepository.findAll()).thenReturn(userList);
        when(userRepository.findById(any(Integer.class))).thenReturn(userOptional);
        when(userRepository.findByUsername(any(String.class))).thenReturn(user);
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class FindAllTests {
        @Test
        public void findAllTest () {
            assertEquals(userList, userService.findAll());
            verify(userRepository, Mockito.times(1)).findAll();
        }
    }

    @Nested
    public class FindByIdTests {
        @Test
        public void findByIdTest() {
            assertEquals(userOptional, userService.findById(user.getId()));
            verify(userRepository, Mockito.times(1)).findById(any(Integer.class));
        }

        @Test
        public void findByIdTestIfNotFound() {
            when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertEquals(Optional.empty(), userService.findById(user.getId()));
            verify(userRepository, Mockito.times(1)).findById(any(Integer.class));
        }

        @Test
        public void findByIdTestIfIdNotValid() {
            assertThrows(IllegalArgumentException.class, () -> userService.findById(null));
            verify(userRepository, Mockito.times(0)).findById(any(Integer.class));
        }
    }

    @Nested
    public class FindByUsernameTests {
        @Test
        public void findByUsernameTest () {
            assertEquals(user, userService.findByUsername(user.getUsername()));
            verify(userRepository, Mockito.times(1)).findByUsername(any(String.class));
        }
        @Test
        public void findByUsernameTestIfIdNotValid () {
            assertThrows(IllegalArgumentException.class, () -> userService.findByUsername(null));
            verify(userRepository, Mockito.times(0)).findByUsername(any(String.class));
        }
    }

    @Nested
    public class DeleteByIdTests {
        @Test
        public void deleteByIdTest() {
            userService.deleteById(user.getId());
            verify(userRepository, Mockito.times(1)).deleteById(any(Integer.class));
        }

        @Test
        public void deleteByIdTestIfIdNotValid() {
            assertThrows(IllegalArgumentException.class, () -> userService.deleteById(null));
            verify(userRepository, Mockito.times(0)).deleteById(any(Integer.class));
        }
    }
}
