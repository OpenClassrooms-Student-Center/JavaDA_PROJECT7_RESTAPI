package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@SpringBootTest
public class UserServiceTest {

    private IUserService userService;

    private static User userToAdd = new User();

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
	MockitoAnnotations.openMocks(this);

	userService = new UserServiceImpl(userRepository);
	userToAdd.setId(0);
    }

    @Test
    public void shouldGetAllUsers() {
	List<User> userList = new ArrayList<>();
	userList.add(userToAdd);

	when(userRepository.findAll()).thenReturn(userList);

	List<User> users = (List<User>) userService.getUsers();
	assertEquals(users.get(0).getId(), userToAdd.getId());
    }

    @Test
    public void shouldGetOneUser() {
	when(userRepository.findById(userToAdd.getId())).thenReturn(Optional.of(userToAdd));

	Optional<User> user = userService.getUserById(userToAdd.getId());

	assertEquals(user.get().getId(), userToAdd.getId());
    }

    @Test
    public void shouldSaveUser() {
	when(userRepository.save(any(User.class))).thenReturn(userToAdd);

	User userUser = userService.saveUser(userToAdd);

	assertEquals(userUser.getId(), userToAdd.getId());
    }

    @Test
    public void shouldDeleteUser() {
	userService.deleteUserById(userToAdd.getId());

	verify(userRepository, times(1)).deleteById(userToAdd.getId());
    }

}
