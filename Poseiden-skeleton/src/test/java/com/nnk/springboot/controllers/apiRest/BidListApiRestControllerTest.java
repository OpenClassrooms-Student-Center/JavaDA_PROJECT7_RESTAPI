package com.nnk.springboot.controllers.apiRest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.BidListRepository;
import org.json.JSONException;
import org.json.JSONObject;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        user1 = new User("Jimmy", "Jimmy", "12345","ADMIN");

    }

    // Format test
    // Given
    // When
    // Then

//    @Test
//    void showRestBid() throws Exception {
//        String connectedUser = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        //        UserDetails user = User.builder()
////                .username("user")
////                .password("$2a$12$uCNqI/ReIfRsZ/A/dfTl9O1LQCfiZhJ7AHctjBOQWdR9xSspaMa6m")
////                .roles("USER")
////                .build();
//        GrantedAuthority authority = new SimpleGrantedAuthority(user1.getRole());
//        when(userDetailService.loadUserByUsername(any()))
//                .thenReturn((UserDetails) new User(user1.getUsername(), user1.getPassword(), String.valueOf(Collections.singletonList(authority))));
//
//        when(bidListService.findAll()).thenReturn(any());
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/api")
//                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
//
//
//    }

    @Test
    void showRestBidById() {
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
    void uploadRestBid() {
    }

    @Test
    void deleteRestBid() {
    }
}