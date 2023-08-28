package com.nnk.springboot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.nnk.springboot.domain.Users;
import com.nnk.springboot.repositories.UsersRepository;
import com.nnk.springboot.service.LoggerApi;
import com.nnk.springboot.service.MyUserDetailService;
import com.nnk.springboot.service.UsersService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MyUserDetailServiceTests {

    @Autowired
    private MyUserDetailService myUserDetailService;

    @MockBean
    private static Users users;

    @MockBean
    private static UsersRepository userRepository;

    @MockBean
    private static UsersService userService;

    @MockBean
    private static UsernameNotFoundException usernameNotFoundException;

    // Récupération de notre logger.
    private static final Logger LOGGER = LogManager.getLogger(UserControllerTests.class);

    @BeforeAll
    public static void activateLoggerForTests() {
        LoggerApi loggerApi = new LoggerApi();
        loggerApi.setLoggerForTests();

    }

    /**
     * @throws Exception
     */
    @Test
    public void testLoadUserByUsername() throws Exception {

        String username = "User_Name_Test";

        // user = not null
        users = new Users();
        users.setUsername(username);
        users.setPassword("password");
        users.setRole("ROLE_USER_TEST");
        users.setFullname(username);
        users.setId(1);

        when(userService.getUser(username)).thenReturn(users);

        UserDetails myUserDetails = myUserDetailService.loadUserByUsername(username);

        assertEquals(users.getUsername(), myUserDetails.getUsername());

    }

    
    /** 
     * @throws Exception
     */
    // @Test
    public void testLoadUserByUsernameUserNull() throws Exception {

        String username = "User_Name_Test";

        // user = null
        users = null;
        when(userService.getUser(username)).thenReturn(users);

        when(usernameNotFoundException).thenThrow(NullPointerException.class);

        UserDetails myUserDetails = myUserDetailService.loadUserByUsername(username);

        assertEquals(users.getUsername(), myUserDetails.getUsername());

    }
}