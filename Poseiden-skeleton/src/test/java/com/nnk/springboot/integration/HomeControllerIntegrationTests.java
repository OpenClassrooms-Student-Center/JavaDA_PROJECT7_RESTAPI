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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIntegrationTests extends TestVariables {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class HomeTests {
        @Test
        @WithMockUser(authorities = "ADMIN")
        public void homeTestIfAdmin () throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().is3xxRedirection());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void homeTestIfUser () throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().is3xxRedirection());
        }
        @Test
        @WithMockUser()
        public void homeTest () throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().is2xxSuccessful());
        }
        @Test
        public void homeTestIfNotAuthorized () throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().is3xxRedirection());
        }
    }

    @Nested
    public class AdminHomeTests {
        @Test
        @WithMockUser(authorities = "ADMIN")
        public void adminHomeTest () throws Exception {
            mockMvc.perform(get("/home/admin"))
                    .andExpect(status().is3xxRedirection());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void adminHomeTestIfNotAuthorized () throws Exception {
            mockMvc.perform(get("/home/admin"))
                    .andExpect(status().isForbidden());
        }
    }
}
