package com.nnk.springboot.controllers.apiRest;

import com.nimbusds.jose.shaded.json.JSONObject;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Assertions;
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
@WithMockUser(username = "User")
class RatingApiRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;


    // Format test
    // Given
    // When
    // Then

    @Test
    void showRestRating() throws Exception {

        //GIVEN
        JSONObject json = new JSONObject();
        json.put("moodysRating", "toto");
        json.put("sandRating", "tutu");
        json.put("fitchRating", 20);
        json.put("orderNumber", 2);


        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/ratings/api"))
                .andExpect(status().isOk());
        //THEN
        List<Rating> result = ratingRepository.findAll();
        assertEquals("toto", result.get(0).getMoodysRating());

    }

    @Test
    void showRestRatingById() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("moodysRating", "toto");
        json.put("sandRating", "tutu");
        json.put("fitchRating", 20);
        json.put("orderNumber", 2);


        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rating/api/{id}", 1))
                .andExpect(status().isOk());
        //THEN
        Optional<Rating> result = ratingRepository.findById(1);
        assertEquals("toto", result.get().getMoodysRating());
    }

//    @Test
//    void showRestById_shouldReturnException() throws Exception {
//
//        //WHEN
//        mockMvc.perform(get("/rating/api"));
//
//        //THEN
//        DataNotFoundException thrown = Assertions.assertThrows(DataNotFoundException.class, () -> {
//            Optional<Rating> rating = ratingRepository.findById(2);
//
//        });
//        Assertions.assertEquals("Id not present", thrown.getMessage());
//    }

    @Test
    void addRestRating() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("moodysRating", "toto");
        json.put("sandRating", "tutu");
        json.put("fitchRating", 20);
        json.put("orderNumber", 2);


        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        //THEN
        List<Rating> result = ratingRepository.findAll();
        assertEquals(1, result.size());

    }

    @Test
    void uploadRestRating() throws Exception {

        //GIVEN
        JSONObject json = new JSONObject();
        json.put("moodysRating", "toto");
        json.put("sandRating", "tutu");
        json.put("fitchRating", 20);
        json.put("orderNumber", 2);


        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());

        JSONObject json1 = new JSONObject();
        json1.put("id", 1);
        json1.put("moodysRating", "tata");
        json1.put("sandRating", "rating1");
        json1.put("fitchRating", 20);
        json1.put("orderNumber", 2);

        mockMvc.perform(MockMvcRequestBuilders.put("/rating/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1.toString()))
                .andExpect(status().isOk());
        //THEN
        List<Rating> result = ratingRepository.findAll();
        assertEquals(1, result.size());


    }

    @Test
    void deleteRestRating() throws Exception {
        //GIVEN
        JSONObject json = new JSONObject();
        json.put("moodysRating", "toto");
        json.put("sandRating", "tutu");
        json.put("fitchRating", 20);
        json.put("orderNumber", 2);


        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.toString()))
                .andExpect(status().isOk());


        mockMvc.perform(MockMvcRequestBuilders.delete("/rating/api/{id}",1))
                .andExpect(status().isOk());
        //THEN
        List<Rating> result = ratingRepository.findAll();
        assertEquals(0, result.size());
    }
}