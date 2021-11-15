package com.nnk.springboot.unit.controller;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RatingController.class)
@AutoConfigureMockMvc(secure = false)
public class RatingControllerTest {
    @MockBean
    RatingService ratingService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testList() throws Exception {
        Rating rating = new Rating("moodys", "sandP", "fitch", 1);
        Mockito.when(ratingService.findAll()).thenReturn(Arrays.asList(rating));
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(content().string(Matchers.containsString(String.valueOf(rating.getMoodys()))))
                .andExpect(content().string(Matchers.containsString(String.valueOf(rating.getSandP()))))
                .andExpect(content().string(Matchers.containsString(String.valueOf(rating.getFitch()))))
                .andExpect(content().string(Matchers.containsString(String.valueOf(rating.getOrder()))));
    }

    @Test
    public void testAddRating() throws Exception {
        Rating rating = new Rating();
        mockMvc.perform(get("/rating/add", rating))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    public void testRatingValidate() throws Exception {
        Rating rating = new Rating("moodysRating", "sandPRating", "fitchRating", 1);
        Mockito.when(ratingService.saveOrUpdate(rating)).thenReturn(Arrays.asList(rating));
        mockMvc.perform(
        post("/rating/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED).content("moodys=moodysRating&sandP=sandP&fitch=fitch&order=1")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    public void testGetUpdateRating() throws Exception {
        Rating rating = new Rating("moodys", "sandP", "fitch", 1);
        Mockito.when(ratingService.findById(1)).thenReturn(rating);
        mockMvc.perform(get("/rating/update/1").contentType(MediaType.APPLICATION_FORM_URLENCODED).content("moodys=moodys&sandP=sandP&fitch=fitch&order=1")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    public void testUpdateRating() throws Exception {
        Rating rating = new Rating("moodysRating", "sandPRating", "fitchRating", 1);
        Mockito.when(ratingService.saveOrUpdate(rating)).thenReturn(Arrays.asList(rating));
        mockMvc.perform(post("/rating/update/1", rating))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    public void testDeleteRating() throws Exception {
        Rating rating  = new Rating("moodysRating", "sandPRating", "fitchRating", 1);
        Mockito.when(ratingService.delete(2)).thenReturn(Arrays.asList(rating));
        mockMvc.perform(get("/rating/delete/2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"));
    }
}
