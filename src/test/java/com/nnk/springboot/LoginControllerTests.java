package com.nnk.springboot;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.service.LoggerApi;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

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
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "user", password = "test")
    public void testHome() throws Exception {

        mockMvc.perform(get("/app/login")).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "user", password = "test", authorities = "USER")
    public void testLoginOk() throws Exception {

        mockMvc.perform(get("/app/login/ok")).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "user", password = "test", authorities = "ADMIN")
    public void testGetAllUserArticles() throws Exception {

        mockMvc.perform(get("/app/secure/article-details")).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "user", password = "test", authorities = "USER")
    public void testError() throws Exception {

        mockMvc.perform(get("/app/error")).andExpect(status().isOk());

    }

    // @Test
    // @WithMockUser(username = "user", password = "test", authorities = "USER")
    public void testAppLogout() throws Exception {

        mockMvc.perform(post("/app/app-logout").with(csrf())).andExpect(status().isOk());

    }
}