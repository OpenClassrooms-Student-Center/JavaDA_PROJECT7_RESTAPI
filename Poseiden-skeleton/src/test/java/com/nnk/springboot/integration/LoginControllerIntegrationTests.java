package com.nnk.springboot.integration;

import com.nnk.springboot.TestVariables;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerIntegrationTests extends TestVariables {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class LoginTests {
        @Test
        public void loginTest () throws Exception {
            mockMvc.perform(get("/login"))
                    .andExpect(status().is2xxSuccessful());
        }
    }

    @Nested
    public class getAllUserArticlesTests {
        @Test
        @WithMockUser(authorities = "ADMIN")
        public void getAllUserArticlesTest () throws Exception {
            mockMvc.perform(get("/secure/article-details"))
                    .andExpect(status().is2xxSuccessful());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void getAllUserArticlesTestIfNotAuthorized () throws Exception {
            mockMvc.perform(get("/secure/article-details"))
                    .andExpect(status().isForbidden());
        }
    }
}
