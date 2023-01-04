package com.nnk.springboot.controllers.apiRest;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //erase database each test
@WithMockUser(username = "Admin", authorities = { "ADMIN" }) //add Authorities test profile
class UserApiRestControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @Test
    @WithMockUser(username = "Admin", authorities = { "ADMIN" })
    void showRestAllUser() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("username", "Mario");
        json.put("password", "1Qwertyuiop!");
        json.put("fullname", "MarioBros");
        json.put("role", "USER");


        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/user/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/api"))
                .andExpect(status().isOk());
        //THEN
        List<User> result = userRepository.findAll();
        assertEquals(1, result.size());

    }

    @Test
    @WithMockUser(username = "Admin", authorities = { "ADMIN" })
    void showRestUserById_shouldReturnName() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("username", "Mario");
        json.put("password", "1Qwertyuiop!");
        json.put("fullname", "MarioBros");
        json.put("role", "USER");

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/user/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/user/api/{id}",1))
                .andExpect(status().isOk());
        //THEN
        Optional<User> result = userRepository.findById(1);
        assertEquals("Mario", result.get().getUsername());
    }

    @Test
    @WithMockUser(username = "Admin", authorities = { "ADMIN" })
    void addRestUser() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("username", "Mario");
        json.put("password", "1Qwertyuiop!");
        json.put("fullname", "MarioBros");
        json.put("role", "USER");

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/user/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());


        mockMvc.perform(get("/user/api/{id}",1))
                .andExpect(status().isOk());
        //THEN
        Optional<User> result = userRepository.findById(1);
        assertEquals("MarioBros", result.get().getFullname());

    }

    @Test
    void uploadRestUser() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("username", "Mario");
        json.put("password", "1Qwertyuiop!");
        json.put("fullname", "MarioBros");
        json.put("role", "USER");

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/user/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        //GIVEN
        JSONObject json1 = new JSONObject();
        json1.put("id", 1);
        json1.put("username", "Luigi");
        json1.put("password", "1Qwertyuiop!");
        json1.put("fullname", "LuigiBros");
        json1.put("role", "Admin");

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.put("/user/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1.toString()))
                .andExpect(status().isOk());


        mockMvc.perform(get("/user/api/{id}",1))
                .andExpect(status().isOk());
        //THEN
        Optional<User> result = userRepository.findById(1);
        assertEquals("LuigiBros", result.get().getFullname());

    }

    @Test
    @WithMockUser(username = "Admin", authorities = { "ADMIN" })
    void deleteRestUser_shouldReturnSizeListZero() throws Exception {

        //GIVEN
        JSONObject json = new JSONObject();
        json.put("username", "Mario");
        json.put("password", "1Qwertyuiop!");
        json.put("fullname", "MarioBros");
        json.put("role", "USER");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/api/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/api"))
                .andExpect(status().isOk());
        //THEN
        List<User> result = userRepository.findAll();
        assertEquals(0, result.size());
    }
}