package com.nnk.springboot.integration;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingIntegrationTests extends TestVariables {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;

    private Integer databaseSizeBefore;
    private Integer databaseSizeAfter;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        ratingRepository.delete(rating); // DOESN'T WORK ; EVERY TEST CREATES A NEW RATING WITH DIFFERENT ID
        databaseSizeBefore = ratingRepository.findAll().size();
        databaseSizeAfter = null;
    }

    public Integer databaseSizeChange() {
        databaseSizeAfter = ratingRepository.findAll().size();
        return databaseSizeAfter - databaseSizeBefore;
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class homeTests
    {
        @Test
        @WithMockUser
        public void homeTest () throws Exception {
            mockMvc.perform(get("/rating/list"))
                    .andExpect(status().is2xxSuccessful());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class addRatingFormTests
    {
        @Test
        @WithMockUser
        public void addRatingFormTest () throws Exception {
            mockMvc.perform((get("/rating/add")))
                    .andExpect(status().is2xxSuccessful());
            assertEquals(0, databaseSizeChange());
        }
    }
    @Nested
    public class validateTests
    {
        @Test
        @WithMockUser
        public void validateTest () throws Exception {
            mockMvc.perform(post("/rating/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(rating.toString()))
                    .andExpect(status().is2xxSuccessful());
            databaseSizeAfter = ratingRepository.findAll().size();
            assertEquals(1, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void validateTestIfInvalidRating () throws Exception {
            rating.setFitchRating(longString);
            mockMvc.perform(post("/rating/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(rating.toString()))
                    .andExpect(status().is2xxSuccessful());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class showUpdateFormTests
    {
        @Test
        @WithMockUser
        public void showUpdateFormTest () throws Exception {
            mockMvc.perform((get("/rating/update/" +
                    ((Rating) ratingRepository.findAll().toArray()[0]).getId())))
                    .andExpect(status().is2xxSuccessful());
            assertEquals(0, databaseSizeChange());

            // SHOULD I CHECK IF RATING IS ADDED TO MODEL ?
        }
        @Test
        @WithMockUser
        public void showUpdateFormTestIfNotInDb () throws Exception {
            mockMvc.perform((get("/rating/update/0")))
                    .andExpect(status().is2xxSuccessful());
            assertEquals(0, databaseSizeChange());

            // INCORRECT ID ARBITRARY
        }
    }
}
