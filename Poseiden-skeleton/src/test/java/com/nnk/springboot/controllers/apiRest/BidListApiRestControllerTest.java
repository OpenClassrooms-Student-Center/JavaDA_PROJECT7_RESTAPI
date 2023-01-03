package com.nnk.springboot.controllers.apiRest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
//@WebMvcTest(controllers = BidListApiRestController.class)
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username = "User")
class BidListApiRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private BidListRepository bidListRepository;


    private User user1;


    @BeforeEach
    void setup() {
        new ObjectMapper();
        user1 = new User("Jimmy", "Jimmy", "12345", "ADMIN");

    }

    // Format test
    // Given
    // When
    // Then

    @Test
    void showRestBid() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("account", "toto");
        json.put("type", "tutu");
        json.put("bidQuantity", 20);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/bidList/api"))
                .andExpect(status().isOk());
        //THEN
        List<BidList> result = bidListRepository.findAll();
        assertEquals("toto", result.get(0).getAccount());


    }

    @Test
    void showRestBidById() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("account", "toto");
        json.put("type", "tutu");
        json.put("bidQuantity", 20);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/bidList/api/{id}", 1L))
                .andExpect(status().isOk());
        //THEN
        Optional<BidList> result = bidListRepository.findById(1);
        assertEquals("toto", result.get().getAccount());
    }


    @Test
    void addRestBid() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("account", "toto");
        json.put("type", "tutu");
        json.put("bidQuantity", 20);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());
        //THEN
        List<BidList> result = bidListRepository.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void uploadRestBid() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("bidListId", 1);
        json.put("account", "toto");
        json.put("type", "tutu");
        json.put("bidQuantity", 20);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        JSONObject json1 = new JSONObject();
        json1.put("bidListId", 1);
        json1.put("account", "Sa,");
        json1.put("type", "tutu");
        json1.put("bidQuantity", 18);

        mockMvc.perform(MockMvcRequestBuilders.put("/bidList/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1.toString()))
                .andExpect(status().isOk());
        //THEN
        List<BidList> result = bidListRepository.findAll();
        assertEquals(1, result.size());


    }

    @Test
    void deleteRestBid() throws Exception {

        //GIVEN
        JSONObject json = new JSONObject();
        json.put("bidListId", 1);
        json.put("account", "toto");
        json.put("type", "tutu");
        json.put("bidQuantity", 20);

        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());


        mockMvc.perform(MockMvcRequestBuilders.delete("/bidList/api/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //THEN
        List<BidList> result = bidListRepository.findAll();
        assertEquals(0, result.size());
    }
}