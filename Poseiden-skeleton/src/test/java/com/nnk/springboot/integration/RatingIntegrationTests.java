package com.nnk.springboot.integration;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class RatingIntegrationTests extends TestVariables {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;

    private Integer databaseSizeBefore;

    @BeforeAll
    public void setUpGlobal() {
        System.out.println("---- SETUPGLOBAL");
        initializeVariables();
        ratingRepository.save(rating);
        ratingId = rating.getId();
        System.out.println(ratingId);
    }

    @AfterAll
    public void cleanUpDatabase() {
        ratingRepository.deleteById(ratingId);
    }

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        //ratingRepository.delete(rating); // DOESN'T WORK ; EVERY TEST CREATES A NEW RATING WITH DIFFERENT ID
        databaseSizeBefore = ratingRepository.findAll().size();
        System.out.println("---- SETUPPERTEST");
        System.out.println(databaseSizeBefore);
    }

    public Integer databaseSizeChange() {
        return ratingRepository.findAll().size() - databaseSizeBefore;
    }

    public Boolean resultContainsRating(MvcResult result, Rating rating) throws UnsupportedEncodingException {
        String resultContent = result.getResponse().getContentAsString();
        return resultContent.contains(ratingId.toString())
                && resultContent.contains(rating.getMoodysRating())
                && resultContent.contains(rating.getSandPRating())
                && resultContent.contains(rating.getFitchRating())
                && resultContent.contains(rating.getOrder().toString());
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class homeTests
    {
        @Test
        @WithMockUser
        public void homeTest () throws Exception {
            MvcResult result = mockMvc.perform(get("/rating/list"))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(true, resultContainsRating(result, rating));
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
            MvcResult result = mockMvc.perform((get("/rating/update/" +
                            (ratingId))))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(true, resultContainsRating(result, rating));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void showUpdateFormTestIfNotInDb () throws Exception {
            MvcResult result = mockMvc.perform((get("/rating/update/0")))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(false, resultContainsRating(result, rating));
            assertEquals(0, databaseSizeChange());

            // INCORRECT ID ARBITRARY
        }
    }
    @Nested
    public class updateRatingTests
    {
        @Test
        @WithMockUser
        public void updateRatingTest () throws Exception {
            mockMvc.perform(post("/rating/update/" +
                            ((Rating) ratingRepository.findAll().toArray()[0]).getId())
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(rating.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }

        @Test
        @WithMockUser
        public void updateRatingTestIfInvalidRating () throws Exception {
            rating.setFitchRating(longString);
            mockMvc.perform(post("/rating/update/" +
                            ((Rating) ratingRepository.findAll().toArray()[0]).getId())
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(rating.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void updateRatingTestIfNotInDb () throws Exception {
            mockMvc.perform(post("/rating/update/0")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(rating.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
    }
    @Nested
    public class deleteRatingTests
    {
        @Test
        @WithMockUser
        public void deleteRatingTest () throws Exception {
            ratingRepository.save(rating);
            Object[] ratings = ratingRepository.findAll().toArray();
            rating.setId(((Rating) ratings[ratings.length - 1]).getId());

            mockMvc.perform(get("/rating/delete/" + rating.getId()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void deleteRatingTestIfNotInDb () throws Exception {
            mockMvc.perform(get("/rating/delete/0"))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
    }
}
