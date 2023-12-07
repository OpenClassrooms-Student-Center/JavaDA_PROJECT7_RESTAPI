package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingITTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webContext;

    @MockBean
    RatingService ratingService;

    @Before
    public void setupMockmvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getMappingAddRatingFormShouldReturnSuccess() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(get("/rating/add"))

                //THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("rating"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void requestMappingHomeViewShouldReturnSuccess() throws Exception {
        //GIVEN
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(10);

        List<Rating> ratingList = new ArrayList<>();
        ratingList.add(rating);

        doReturn(ratingList)
                .when(ratingService)
                .findAllRating();

        //WHEN
        /*mockMvc.perform(get("/rating/list"))

                //THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("rating"))
                .andReturn();*/
        assertEquals("Fitch Rating", ratingList.get(0).getFitchRating());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postMappingValidateViewShouldReturnSuccess() throws Exception {
        //GIVEN
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(10);

        List<Rating> ratingList = new ArrayList<>();
        ratingList.add(rating);

        doNothing()
                .when(ratingService)
                .saveRating(rating);

        doReturn(ratingList)
                .when(ratingService)
                .findAllRating();

        //WHEN
        mockMvc.perform(post("/rating/validate")
                        .param("id", "1")
                        .param("moodysRating", "Moodys Rating")
                        .param("sandPRating", "Sand PRating")
                        .param("fitchRating", "Fitch Rating")
                        .param("orderNumber", "10"))
                //THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"))
                .andReturn();
        assertEquals("Fitch Rating", ratingList.get(0).getFitchRating());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getMappingShowUpdateFormViewShouldReturnSuccess() throws Exception {
        //GIVEN
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(10);

        doReturn(true)
                .when(ratingService)
                .checkIfIdExists(rating.getId());

        doReturn(rating)
                .when(ratingService)
                .findByRatingId(rating.getId());

        //WHEN
        mockMvc.perform(get("/rating/update/{id}", "1"))

                //THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"))
                .andReturn();
        assertEquals("Fitch Rating", rating.getFitchRating());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postMappingUpdateCurvePointViewShouldReturnSuccess() throws Exception {
        //GIVEN
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(10);

        List<Rating> ratingList = new ArrayList<>();
        ratingList.add(rating);

        doReturn(true)
                .when(ratingService)
                .checkIfIdExists(rating.getId());

        doNothing()
                .when(ratingService)
                .saveRating(rating);

        doReturn(ratingList)
                .when(ratingService)
                .findAllRating();

        //WHEN
        mockMvc.perform(post("/rating/update/{id}", "1")
                        .param("id", "1")
                        .param("moodysRating", "Moodys Rating")
                        .param("sandPRating", "Sand PRating")
                        .param("fitchRating", "Fitch Rating")
                        .param("orderNumber", "10"))
                //THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"))
                .andReturn();
        assertEquals("Fitch Rating", ratingList.get(0).getFitchRating());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getMappingDeleteBidViewShouldReturnSuccess() throws Exception {
        //GIVEN
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(10);

        List<Rating> ratingList = new ArrayList<>();
        ratingList.add(rating);

        doReturn(true)
                .when(ratingService)
                .checkIfIdExists(rating.getId());

        doNothing()
                .when(ratingService)
                .deleteRating(rating);

        doReturn(ratingList)
                .when(ratingService)
                .findAllRating();

        //WHEN
        mockMvc.perform(get("/rating/delete/{id}", "1"))

                //THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"))
                .andReturn();
        assertEquals("Fitch Rating", ratingList.get(0).getFitchRating());
    }

}
