package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_shouldReturnUser_whenIdExists() {
        User user = new User();
        user.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    void saveUser_shouldEncodePasswordAndSaveUser() {
        User user = new User();
        user.setPassword("rawPassword");
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.saveUser(user);

        assertEquals("encodedPassword", result.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser_shouldEncodePasswordAndUpdateUser() {
        User user = new User();
        user.setId(1);
        user.setPassword("newPassword");
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(userRepository.save(user)).thenReturn(user);

        user.setId(1);
        User result = userService.saveUser(user);

        assertEquals(1, result.getId());
        assertEquals("encodedNewPassword", result.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteUserById_shouldDeleteUser_whenIdExists() {
        doNothing().when(userRepository).deleteById(1);

        userService.deleteUserById(1);

        verify(userRepository, times(1)).deleteById(1);
    }
}
