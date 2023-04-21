package com.nnk.springboot.controllersTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccessControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testUserListPageAccessDenied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testUserListPageAccessGranted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testHomePageAccessDenied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testHomePageAccessGranted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}