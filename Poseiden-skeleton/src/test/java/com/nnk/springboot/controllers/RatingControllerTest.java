package com.nnk.springboot.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.security.UserDetailsServiceImpl;
import com.nnk.springboot.services.RatingService;

@WebMvcTest(controllers = RatingController.class)
class RatingControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private RatingService ratingService;

	@MockBean
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	@WithMockUser
	final void testRatingList() throws Exception {
		mockMvc.perform(get("/rating/list").with(csrf().asHeader())).andExpect(status().isOk())
		.andExpect(view().name("rating/list"));
	}

	@Test
	@WithMockUser
	final void testAddRatingForm() throws Exception {
		mockMvc.perform(get("/rating/add").with(csrf().asHeader())).andExpect(status().isOk())
		.andExpect(view().name("rating/add"));
	}

	@Test
	@WithMockUser
	final void testValidate() throws Exception {
		mockMvc.perform(post("/rating/validate").with(csrf().asHeader()).param("moodysRating", "Mood")
				.param("sandPRating", "Sand").param("fitchRating", "Fitch").param("orderNumber", "2"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/rating/list"));
	}
	
	@Test
	@WithMockUser
	void testValidateHasError() throws Exception {
		mockMvc.perform(post("/rating/validate").with(csrf().asHeader()).param("moodysRating", "Mood")
				.param("sandPRating", "Sand").param("fitchRating", "Fitch").param("orderNumber", "text"))
				.andExpect(status().isOk())
				.andExpect(view().name("rating/add"))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrorCode("rating", "orderNumber", "typeMismatch"));
	}

	@Test
	@WithMockUser
	final void testShowUpdateForm() throws Exception {
		Rating rating = new Rating();
		rating.setId(1);
		rating.setMoodysRating("Mood");
		rating.setSandPRating("Sand");
		rating.setFitchRating("Fitch");
		rating.setOrderNumber(1);
		when(ratingService.findById(1)).thenReturn(rating);
		mockMvc.perform(get("/rating/update/1").with(csrf().asHeader()))
				.andExpect(status().isOk())
				.andExpect(view().name("rating/update"))
				.andExpect(model().hasNoErrors())
				.andExpect(model().attribute("rating", rating));
	}

	@Test
	@WithMockUser
	final void testUpdateRating() throws Exception {
		Rating rating = new Rating();
		when(ratingService.updateRating(rating, 1)).thenReturn(rating);
		mockMvc.perform(post("/rating/update/1").with(csrf().asHeader()).param("moodysRating", "Mood")
				.param("sandPRating", "Sand").param("fitchRating", "Fitch").param("orderNumber", "1"))
				.andExpect(status().isFound())
				.andExpect(view().name("redirect:/rating/list"))
				.andExpect(model().hasNoErrors());
	}
	
	@Test
	@WithMockUser
	void testUpdateRatingHasError() throws Exception {
		mockMvc.perform(post("/rating/update/1").with(csrf().asHeader()).param("moodysRating", "Mood")
				.param("sandPRating", "Sand").param("fitchRating", "Fitch").param("orderNumber", "text"))
				.andExpect(status().isOk())
				.andExpect(view().name("rating/update"))
				.andExpect(model().hasErrors())
				.andExpect(model().attributeHasFieldErrorCode("rating", "orderNumber", "typeMismatch"));
	}
	
	@Test
	@WithMockUser
	void testUpdateRatingThrowEntityNotFoundException() throws Exception {
		when(ratingService.updateRating(Mockito.any(Rating.class), Mockito.anyInt())).thenThrow(EntityNotFoundException.class);
		mockMvc.perform(post("/rating/update/1").with(csrf().asHeader()).param("moodysRating", "Mood")
				.param("sandPRating", "Sand").param("fitchRating", "Fitch").param("orderNumber", "1"))
					.andExpect(status().isFound())
					.andExpect(view().name("redirect:/rating/list"))
					.andExpect(model().hasNoErrors());
	}

	@Test
	@WithMockUser
	final void testDeleteRating() throws Exception {
		mockMvc.perform(get("/rating/delete/0").with(csrf().asHeader()))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:/rating/list"));
	}

}
