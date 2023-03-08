package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;
import com.nnk.springboot.util.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(UserServiceImpl.class)
class UserServiceImplTest {

    private static final User user = new User();
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    private void init() {
        user.setId(1);
        user.setUsername("test");
        user.setPassword("abcdeFk12!");
        user.setFullname("abcde");
        user.setRole("USER");
    }


    @Test
    final void testFindById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        User foundUser = userService.findById(1);

        assertThat(foundUser).isEqualTo(user);

    }

    @Test
    final void testFindByIdNotFound() throws NotFoundException {
        assertThrows(NotFoundException.class, () -> userService.findById(0));
    }

    @Test
    final void testDeleteById() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        userService.delete(1);

        verify(userRepository, times(1)).delete(any(User.class));
    }

    @Test
    final void testFindAll() {
        List<User> findAll = new ArrayList<>();
        findAll.add(user);

        when(userRepository.findAll()).thenReturn(findAll);
        List<User> foundUser = userService.findAll();

        assertThat(foundUser).isEqualTo(findAll);
    }

    @Test
    final void testCreateUser() {
        User userToCreate = new User();
        userToCreate.setUsername("test");
        userToCreate.setPassword("abcdeFk12!");
        userToCreate.setFullname("abcde");
        userToCreate.setRole("USER");

        when(userRepository.save(any(User.class))).thenReturn(new User());
        User result = userService.create(userToCreate);

        verify(userRepository, times(1)).save(any(User.class));
        assertNotNull(result);
    }


    @Test
    final void testUpdateUser() {
        User userToUpdate = new User();
        userToUpdate.setUsername("test");
        userToUpdate.setPassword("abcdeFk12!");
        userToUpdate.setFullname("abc");
        userToUpdate.setRole("USER");

        when(userRepository.findById(1)).thenReturn(Optional.of(UserServiceImplTest.user));
        when(userRepository.getById(1)).thenReturn(UserServiceImplTest.user);
        when(userRepository.save(userToUpdate)).thenReturn(userToUpdate);
        userService.update(1, userToUpdate);

        assertThat(UserServiceImplTest.user.getFullname()).isEqualTo("abc");
        assertThat(UserServiceImplTest.user.getUsername()).isEqualTo("test");
        assertThat(UserServiceImplTest.user.getRole()).isEqualTo("USER");
    }

    @Test
    final void testUpdateUserThrowEntityNotFoundException() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        User userToUpdate = new User();
        userToUpdate.setUsername("test");
        userToUpdate.setPassword("abcdeFk12!");
        userToUpdate.setFullname("abc");
        userToUpdate.setRole("USER");

        assertThrows(NotFoundException.class, () -> userService.update(1, userToUpdate));
    }
}
