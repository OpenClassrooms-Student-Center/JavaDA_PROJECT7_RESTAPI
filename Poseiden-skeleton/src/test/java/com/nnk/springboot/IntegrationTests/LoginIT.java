package com.nnk.springboot.IntegrationTests;

import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class LoginIT {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @BeforeEach
    public void setup() {
    userService.deleteAllUser();
    mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
}
    @Test
    void testLoginSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/validate").with(csrf())
                .param("fullname","testFullname")
                .param("username","testUser")
                .param("password","testPassword123*")
                .param("role","USER"));
        mockMvc.perform(formLogin("/login").user("testUser").password("testPassword123*"))
                .andExpect(authenticated());
    }
    @Test
    void testLoginFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/validate").with(csrf())
                .param("fullname","testFullname")
                .param("username","testUser")
                .param("password","testPassword123*")
                .param("role","USER"));
        mockMvc.perform(formLogin("/login").user("testUser").password("wrongpassword"))
                .andExpect(unauthenticated());
    }

}
