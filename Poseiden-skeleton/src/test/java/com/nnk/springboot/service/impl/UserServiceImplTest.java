package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.web.dto.UserRegistrationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    private PasswordEncoder encoder;

    @Captor
    ArgumentCaptor<User> userCaptor;


    private User user1;
    private User user2;


    @BeforeEach
    void setUp() {
        user1 = new User("Jimmy", "Jimmy", "12345");
        user2 = new User("Margot", "Lupin", "12345");


    }


    @Test
    void saveUserTestShouldReturnUserName() {
        //Given
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setUsername(user1.getUsername());
        userRegistrationDto.setFullname(user1.getFullname());
        userRegistrationDto.setPassword(user1.getPassword());
        when(userService.userExist(any())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user1);

        //When
        User result = userService.saveUser(userRegistrationDto, user1.getPassword());

        //Then
        assertEquals("Jimmy", result.getUsername());


    }

    @Test
    void saveUserTestShouldReturnUserNameException() throws UsernameNotFoundException {
        //Given
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setUsername(user1.getUsername());
        userRegistrationDto.setFullname(user1.getFullname());
        userRegistrationDto.setPassword(user1.getPassword());

        when(userService.userExist(any())).thenReturn(true);


        //When //Then
        assertThrows(UsernameNotFoundException.class, () -> userService.saveUser(userRegistrationDto, user1.getPassword()));


    }

    @Test
    void findAllUsers() {

        //Given
        List<User> allUsers = List.of(
                new User(user1.getUsername(), user1.getFullname(), user1.getPassword()),
                new User(user2.getUsername(), user2.getFullname(), user2.getPassword())
        );

        when(userRepository.findAll()).thenReturn(allUsers);


        //When
        List<User> userResult = userService.findAll();

        //Then
        Assertions.assertEquals(userResult.size(), 2);


    }

    @Test
    void findUserById() {

        //Given
        User user = new User();
        user.setId(1);
        user.setFullname("Jimmy");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));


        //When
        Optional<User> userResult = userService.findById(1);

        //Then
        Assertions.assertEquals(userResult.get().getFullname(), "Jimmy");

    }

    @Test
    void findUserById_ShouldReturnException() throws DataNotFoundException {

        //Given
        when(userRepository.findById(3)).thenReturn(Optional.empty());

        //When //Then
        assertThrows(DataNotFoundException.class, () -> userService.findById(3));

    }

    @Test
    void saveUser() {
        //Given

        User user = new User();
        user.setId(1);
        user.setFullname("Jimmy");
        when(userRepository.save(any(User.class))).thenReturn(user1);

        //When
        User result = userService.save(user);

        //Then
        assertEquals("Jimmy", result.getFullname());

    }

    @Test
    void updateUser() throws UsernameNotFoundException {

        //Given
        List<User> userList = new ArrayList<>();
        user1.setId(1);
        userList.add(user1);
        userList.add(user2);
        when(userRepository.findById(anyInt())).thenReturn(Optional.ofNullable(user1));

        //When
        userService.update(userList.get(0));

        //Then
        verify(userRepository, Mockito.times(1)).findById(any());

    }

    @Test
    void updateUser_ShouldReturnException() throws UsernameNotFoundException {

        //Given
        List<User> userList = new ArrayList<>();
        user1.setId(1);
        userList.add(user1);
        userList.add(user2);
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());


        //When //Then
        assertThrows(UsernameNotFoundException.class, () -> userService.update(userList.get(0)));
    }

    @Test
    void deleteUserById() {

        //Given
        User user = new User();
        user.setId(1);
        user.setFullname("NewUser");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        //When
        userService.delete(1);
        //Then
        verify(userRepository).findById(1);

    }

    @Test
    void deleteUserById_ShouldReturnException() throws DataNotFoundException {
        //Given // When //Then
        assertThrows(DataNotFoundException.class, () -> userService.delete(3));
    }


}