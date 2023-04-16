package com.nnk.springboot.ServicesTest;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.Assert;
//import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
//import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.util.AssertionErrors.assertTrue;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
   // @Mock
   // private PasswordEncoder passwordEncoder;
    @Mock
    private Authentication auth;


    UserService userService;

    User user = new User();


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user.setFullname("johntest");
        user.setUsername("jtest");
        user.setPassword("passwordTest");
        user.setRole("ROLE_ADMIN");
        userService = new UserService(userRepository);
    }

    @Test
    public void findAllUsersTest(){
        //ARRANGE
        List<User> listOfUsers = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(listOfUsers);
        //ACT and ASSERT
        assertEquals(listOfUsers, userService.findAllUsers());
    }

    @Test
    public void registerNewUserTest() throws Exception {
        //ARRANGE
        User user = new User("jtest", "PasswordTest", "johntest", "ROLE_ADMIN");
        when(userRepository.save(any(User.class))).thenReturn(user);
        //ACT
        User registeredUser = userService.registerNewUser(user);

        // Check that the user was added to the repository
        //List<User> allUsers = userService.findAllUsers();
        //assertThat(allUsers.contains(user));
        //ASSERT
        assertNotNull(registeredUser);
        assertEquals("jtest", registeredUser.getUsername());
        assertEquals("PasswordTest", registeredUser.getPassword());
        assertEquals("johntest", registeredUser.getFullname());
        assertEquals("ROLE_ADMIN", registeredUser.getRole());
        verify(userRepository, times(1)).save(registeredUser);
        //verify(passwordEncoder, times(1)).encode("passwordTest");

    }
    @Test
    public void deleteUserByUsernameTest(){
        //ARRANGE
        when(userRepository.findUserByUsername("jtest")).thenReturn(user);
        doNothing().when(userRepository).delete(user);
        //ACT
        userService.deleteUserByUsername("jtest");
        //ASSERT
        verify(userRepository, times(1)).delete(user);
    }
    @Test
    public void userNameOfCurrentUserTest(){

        SecurityContextHolder.getContext().setAuthentication(auth);

        when(auth.getName()).thenReturn("jtest");

        //ACT
        String userNameOfCurrentUser = userService.userNameOfCurrentUser();
        //ASSERT
        assertEquals("jtest", userNameOfCurrentUser );
        verify(auth, times(1)).getName();
    }

    @Test
    public void findByIdTest(){
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        assertEquals(user,userService.findById(1));
    }


}
