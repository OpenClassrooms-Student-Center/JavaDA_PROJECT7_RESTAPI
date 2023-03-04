package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.service.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RatingController.class)
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @Test
    @WithMockUser
    public void testHome() throws Exception {
        List<Rating> rating = Arrays.asList(new Rating(), new Rating(), new Rating());
        when(ratingService.findAll()).thenReturn(rating);

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attribute("ratingList", rating));

        verify(ratingService).findAll();
    }

    @Test
    @WithMockUser
    public void testAddBidForm() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("rating"))
                .andExpect(model().attribute("rating", instanceOf(RatingDto.class)));
    }

    @Test
    @WithMockUser
    public void testValidateWithValidInput() throws Exception {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setFitchRating("A");
        ratingDto.setSandPRating("B");
        ratingDto.setMoodysRating("AA");
        ratingDto.setOrderNumber(4);

        mockMvc.perform(post("/rating/validate")
                        .with(csrf())
                        .param("fitchRating", ratingDto.getFitchRating())
                        .param("moodysRating", ratingDto.getMoodysRating())
                        .param("sandPRating", ratingDto.getSandPRating())
                        .param("orderNumber", String.valueOf(ratingDto.getOrderNumber())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService).create(any(RatingDto.class));
    }

    @Test
    @WithMockUser
    public void testValidateWithInvalidInput() throws Exception {
        RatingDto ratingDto = new RatingDto();

        mockMvc.perform(post("/rating/validate")
                        .with(csrf())
                        .param("fitchRating", ratingDto.getFitchRating())
                        .param("moodysRating", ratingDto.getMoodysRating())
                        .param("sandPRating", ratingDto.getSandPRating())
                        .param("orderNumber", String.valueOf(ratingDto.getOrderNumber())))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("rating"))
                .andExpect(model().attributeHasFieldErrors("rating", "fitchRating", "moodysRating", "sandPRating", "orderNumber"));

    }

    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        Rating rating = new Rating();
        rating.setId(1);
        when(ratingService.findById(1)).thenReturn(rating);

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"))
                .andExpect(model().attribute("rating", rating));

        verify(ratingService).findById(1);
    }

    @Test
    @WithMockUser
    public void updateBidFormShouldReturnValidView() throws Exception {
        int ratingId = 1;
        RatingDto ratingDto = new RatingDto();
        ratingDto.setFitchRating("A");
        ratingDto.setSandPRating("B");
        ratingDto.setMoodysRating("AA");
        ratingDto.setOrderNumber(4);

        given(ratingService.findById(ratingId)).willReturn(new Rating(ratingDto));

        mockMvc.perform(get("/rating/update/" + ratingId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("rating"))
                .andExpect(view().name("rating/update"));
    }

    @Test
    @WithMockUser
    public void updateBidShouldReturnValidView() throws Exception {
        int id = 1;
        RatingDto ratingDto = new RatingDto();
        ratingDto.setFitchRating("A");
        ratingDto.setSandPRating("B");
        ratingDto.setMoodysRating("AA");
        ratingDto.setOrderNumber(4);

        given(ratingService.update(eq(id), any(RatingDto.class))).willReturn(new Rating(ratingDto));

        mockMvc.perform(post("/rating/update/" + id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("fitchRating", ratingDto.getFitchRating())
                        .param("moodysRating", ratingDto.getMoodysRating())
                        .param("sandPRating", ratingDto.getSandPRating())
                        .param("orderNumber", String.valueOf(ratingDto.getOrderNumber())))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @WithMockUser
    public void deleteBidShouldReturnValidView() throws Exception {
        int id = 1;

        mockMvc.perform(get("/rating/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(model().hasNoErrors());

        verify(ratingService).delete(id);
    }
}
