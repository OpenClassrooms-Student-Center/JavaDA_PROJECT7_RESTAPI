package com.nnk.springboot.ServicesTest;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;


//@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
@ExtendWith(SpringExtension.class)
public class UserServiceTest {
   // private UserService userService;

    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;
    /*@BeforeAll
    public void setUp() {

        // Create userService instance with initialized dependencies
         userService = new UserService(userRepository, passWordEncoder);
    }*/
    @Test
    public void registerNewUserTest() {
       userService = new UserService(userRepository, passwordEncoder);
        // Create a new user
        User user = userService.registerNewUser("jtest", "PasswordTest", "johntest", "ROLE_ADMIN");

        // Check that the user was added to the repository
        List<User> allUsers = userService.findAllUsers();
        assertThat(allUsers.contains(user));

        // Check that the user has the expected properties
        assertEquals("jtest", user.getUsername());
        assertEquals("johntest", user.getFullname());
        assertEquals("ROLE_ADMIN", user.getRole());
    }
}
