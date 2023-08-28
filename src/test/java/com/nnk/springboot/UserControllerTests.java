package com.nnk.springboot;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.domain.Users;
import com.nnk.springboot.repositories.UsersRepository;
import com.nnk.springboot.service.LoggerApi;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private Users user;

    @MockBean
    private UsersRepository userRepository;

    @MockBean
    private BCryptPasswordEncoder encoder;

    // Récupération de notre logger.
    private static final Logger LOGGER = LogManager.getLogger(UserControllerTests.class);

    @BeforeAll
    public static void activateLoggerForTests() {
        LoggerApi loggerApi = new LoggerApi();
        loggerApi.setLoggerForTests();

    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                // import static
                // org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
                // import static
                // org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
                // .defaultRequest(get("/").with(user("userNameConnected").roles("USER")))
                // import static
                // org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
                // .defaultRequest(post("/").with(user("userNameConnected").roles("USER")))

                .apply(springSecurity())
                .build();
    }

    /**
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "user", password = "test", authorities = "ADMIN")
    public void testHome() throws Exception {

        /*
         * User userApi = new User();
         * userApi.setFullname("Last Name User");
         * userApi.setUsername("UserName");
         * userApi.setRole("USER");
         * userApi.setPassword("password");
         * List<User> usersList = new ArrayList<>();
         * usersList.add(userApi);
         * 
         * when(userRepository.findAll()).thenReturn(usersList);
         * 
         */
        mockMvc.perform(get("/user/list")).andExpect(status().isOk());

    }

    
    /** 
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "user", password = "test", authorities = "ADMIN")
    public void testValidate() throws Exception {

        mockMvc.perform(post("/user/validate").with(csrf())
                .param("username", "UserName")
                .param("password", "Password10*")
                .param("fullname", "Last Name User")
                .param("role", "ADMIN")).andExpect(status().isFound()); // respose 302
    }

    @Test
    @WithMockUser(username = "user", password = "test", authorities = "ADMIN")
    public void testValidateWithHasError() throws Exception {

        // One parameter missing => error or all parameters missing => error
        mockMvc.perform(post("/user/validate").with(csrf())
                // .param("username", "UserName")
                .param("password", "password")
                .param("fullname", "Last Name User")
                .param("role", "ADMIN")).andExpect(status().isBadRequest()); // respose 400

    }

    @Test
    @WithMockUser(username = "user", password = "test", authorities = "ADMIN")
    public void testShowUpdateForm() throws Exception {

        String idString = "1";
        Users userApi = new Users();
        userApi.setId(Integer.parseInt(idString));
        userApi.setFullname("Last Name User");
        userApi.setUsername("UserName");
        userApi.setRole("ADMIN");
        userApi.setPassword("password");
        Optional<Users> optionalUser = Optional.of(userApi);

        when(userRepository.findById(Integer.parseInt(idString))).thenReturn(optionalUser);
        mockMvc.perform(get("/user/update/{id}", idString)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "test", authorities = "ADMIN")
    public void testUpdateUser() throws Exception {

        String idString = "1";
        mockMvc.perform(post("/user/update/{id}", idString).with(csrf())
                .param("username", "UserName")
                .param("password", "Password10*")
                .param("fullname", "Last Name User")
                .param("role", "ADMIN")).andExpect(status().isFound()); // respose 302
    }

    @Test
    @WithMockUser(username = "user", password = "test", authorities = "ADMIN")
    public void testUpdateUserWithHasError() throws Exception {

        // One parameter missing => error or all parameters missing => error
        String idString = "1";
        mockMvc.perform(post("/user/update/{id}", idString).with(csrf())
                .param("username", "UserName")
                // .param("password", "password")
                .param("fullname", "Last Name User")
                .param("role", "ADMIN")).andExpect(status().isBadRequest()); // respose 400
    }

    @Test
    @WithMockUser(username = "user", password = "test", authorities = "ADMIN")
    public void testDeleteUser() throws Exception {

        String idString = "1";
        Users userApi = new Users();
        userApi.setId(Integer.parseInt(idString));
        userApi.setFullname("Last Name User");
        userApi.setUsername("UserName");
        userApi.setRole("ADMIN");
        userApi.setPassword("password");
        Optional<Users> optionalUser = Optional.of(userApi);

        when(userRepository.findById(Integer.parseInt(idString))).thenReturn(optionalUser);
        mockMvc.perform(get("/user/delete/{id}", idString)).andExpect(status().isFound());
    }

}