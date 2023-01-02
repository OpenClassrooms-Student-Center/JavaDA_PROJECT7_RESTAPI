package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailServiceImplTest {

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private UserDetailServiceImpl userDetailsService;

    @Test
    void loadUserByUsername_Test_ShouldReturnTrue() {
        //Given
        User user = new User("Frank", "Palumbo", "palumbo@mail.com", "12345");
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        //When
        userDetailsService.loadUserByUsername(user.getUsername());

        //Then
        verify(userRepository, Mockito.times(1)).findUserByUsername(user.getUsername());
        assertEquals("Frank", user.getUsername());

    }


    @Test
    void loadUserByUsername_Test_shouldReturnException() throws UsernameNotFoundException{

        User user = new User("Frank", "Zappa", "12345@mail.com" );

        UsernameNotFoundException result = assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("Invalid username and password."));

        assertEquals(result.getMessage(), "Invalid username and password.");


    }
}