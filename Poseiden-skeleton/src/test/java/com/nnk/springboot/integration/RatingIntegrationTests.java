package com.nnk.springboot.integration;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RatingIntegrationTests extends TestVariables {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingRepository ratingRepository;

    private Integer databaseSizeBefore;

    @BeforeAll
    public void setUpGlobal() {
        initializeVariables();
        ratingRepository.save(rating);
        ratingId = rating.getId();
    }

    @AfterAll
    public void cleanUpDatabase() {
        ratingRepository.deleteById(ratingId);
    }

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        databaseSizeBefore = ratingRepository.findAll().size();
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
    public class HomeTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void homeTest () throws Exception {
            MvcResult result = mockMvc.perform(get("/rating/list"))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(true, resultContainsRating(result, rating));
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class AddRatingFormTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void addRatingFormTest () throws Exception {
            mockMvc.perform((get("/rating/add")))
                    .andExpect(status().is2xxSuccessful());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void addRatingFormTestIfNotAuthorized () throws Exception {
            mockMvc.perform((get("/rating/add")))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class ValidateTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void validateTest () throws Exception {
            mockMvc.perform(post("/rating/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(rating.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(1, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void validateTestIfInvalidRating () throws Exception {
            rating.setFitchRating(longString126);
            mockMvc.perform(post("/rating/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(rating.toString()))
                    .andExpect(status().is2xxSuccessful());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void validateTestIfNotAuthorized () throws Exception {
            mockMvc.perform(post("/rating/validate")
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(rating.toString()))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class ShowUpdateFormTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void showUpdateFormTest () throws Exception {
            MvcResult result = mockMvc.perform((get("/rating/update/" + ratingId)))
                    .andExpect(status().is2xxSuccessful())
                    .andReturn();
            assertEquals(true, resultContainsRating(result, rating));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void showUpdateFormTestIfNotInDb () {
            assertThrows(ServletException.class, () -> mockMvc.perform((get("/rating/update/0"))));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void showUpdateFormTestIfNotAuthorized () throws Exception {
            mockMvc.perform((get("/rating/update/" + ratingId)))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class UpdateRatingTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void updateRatingTest () throws Exception {
            mockMvc.perform(post("/rating/update/" + ratingId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(rating.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void updateRatingTestIfInvalidRating () throws Exception {
            rating.setFitchRating(longString126);
            mockMvc.perform(post("/rating/update/" + ratingId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(rating.toString()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void updateRatingTestIfNotInDb () {
            assertThrows(ServletException.class, () -> mockMvc.perform(post("/rating/update/0")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content(rating.toString())));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void updateRatingTestIfNotAuthorized () throws Exception {
            mockMvc.perform(post("/rating/update/" + ratingId)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(rating.toString()))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }

    @Nested
    public class DeleteRatingTests {
        @Test
        @WithMockUser(authorities = "USER")
        public void deleteRatingTest () throws Exception {
            ratingRepository.save(rating);
            mockMvc.perform(get("/rating/delete/" + rating.getId()))
                    .andExpect(status().is3xxRedirection());
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser(authorities = "USER")
        public void deleteRatingTestIfNotInDb () {
            assertThrows(ServletException.class, () -> mockMvc.perform(get("/rating/delete/0")));
            assertEquals(0, databaseSizeChange());
        }
        @Test
        @WithMockUser
        public void deleteRatingTestIfNotAuthorized () throws Exception {
            mockMvc.perform(get("/rating/delete/" + ratingId))
                    .andExpect(status().isForbidden());
            assertEquals(0, databaseSizeChange());
        }
    }
}
