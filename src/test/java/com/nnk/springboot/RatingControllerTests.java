package com.nnk.springboot;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private Rating rating;

    @MockBean
    private RatingRepository ratingRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testHome() throws Exception {

        mockMvc.perform(get("/rating/list")).andExpect(status().isOk());

    }

    @Test
    public void testValidate() throws Exception {

        mockMvc.perform(post("/rating/validate").with(csrf())
                .param("moodysRating", "moodysRating")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "99"))
                .andDo(print())
                .andExpect(status().isFound()); // respose 302
    }

    @Test
    public void testValidateWithHasError() throws Exception {

        // Type orderNumber is string => has error
        mockMvc.perform(post("/rating/validate").with(csrf())
                .param("orderNumber", "string"))
                .andDo(print())
                .andExpect(status().isBadRequest()); // respose 400

    }

    @Test
    public void testShowUpdateForm() throws Exception {

        String idString = "1";
        Rating rating = new Rating();
        rating.setId(Integer.parseInt(idString));
        rating.setFitchRating("FirtchRating");
        Optional<Rating> optionalRating = Optional.of(rating);

        when(ratingRepository.findById(Integer.parseInt(idString))).thenReturn(optionalRating);
        mockMvc.perform(get("/rating/update/{id}", idString)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateRating() throws Exception {

        String idString = "1";
        mockMvc.perform(post("/rating/update/{id}", idString).with(csrf())
                .param("moodysRating", "moodysRating")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "99"))
                .andDo(print()).andExpect(status().isFound()); // respose 302
    }

    @Test
    public void testUpdateRatingWithHasError() throws Exception {

        // Type orderNumber is string => has error
        String idString = "1";
        mockMvc.perform(post("/rating/update/{id}", idString).with(
                csrf())
                .param("moodysRating", "moodysRating")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "orderNumber"))
                .andDo(print())
                .andExpect(status().isBadRequest()); // respose 400
    }

    @Test
    public void testDeleteRating() throws Exception {

        String idString = "1";
        Rating rating = new Rating();
        rating.setId(Integer.parseInt(idString));
        rating.setFitchRating("FitchRating");
        Optional<Rating> optionalRating = Optional.of(rating);

        when(ratingRepository.findById(Integer.parseInt(idString))).thenReturn(optionalRating);
        mockMvc.perform(get("/rating/delete/{id}", idString)).andExpect(status().isFound());
    }

}