package com.nnk.springboot.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class RatingControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private RatingService ratingService;
	
	@Test
	public void test_RatingPages_NeedAuthentication() throws Exception {
		mockMvc.perform(get("curvePoint/list"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("http://localhost/login"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitRatingListAccess() throws Exception{
		mockMvc.perform(get("/rating/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("rating/list"));
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthentication_PermitRatingListAccess() throws Exception{
		mockMvc.perform(get("/rating/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("rating/list"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitRatingAddAccess() throws Exception{
		mockMvc.perform(get("/rating/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("rating/add"));
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthentication_PermitRatingAddAccess() throws Exception{
		mockMvc.perform(get("/rating/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("rating/add"));
	}
	
	@Test
	@WithMockUser(username = "adminTest", password = "Passw0rd!", authorities = "ADMIN")
	public void test_AdminAuthentication_PermitRatingUpdateAccess() throws Exception{
		// saves a rating to update in database
		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
		rating = ratingService.saveRating(rating);
		
		// tries to access to update page
		mockMvc.perform(get("/rating/update/" + rating.getId()))
			.andExpect(status().isOk())
			.andExpect(view().name("rating/update"));
		
		// deletes the rating from the database
		ratingService.deleteRating(rating);
	}

	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UserAuthenticationPermit_RatingUpdateAccess() throws Exception{
		// saves a rating to update in database
		Rating rating = new Rating("Moodys Rating Test", "Sand PRating Test", "Fitch Rating", 10);
		rating = ratingService.saveRating(rating);
		
		// tries to access to update page
		mockMvc.perform(get("/rating/update/" + rating.getId()))
			.andExpect(status().isOk())
			.andExpect(view().name("rating/update"));
		
		// deletes the rating from the database
		ratingService.deleteRating(rating);
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_DeleteARating() throws Exception {
		// saves a rating to delete in database
		Rating rating = new Rating("Moodys Rating Test", "Sand PRating Test", "Fitch Rating Test", 10);
		rating = ratingService.saveRating(rating);
		
		// deletes the rating from database
		mockMvc.perform(get("/rating/delete/{id}", rating.getId()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/rating/list"));		
	}
	
	@Test(expected = NestedServletException.class)
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotDeleteARating_withInvalidId() throws Exception {
		
		// tries to delete a rating from database with invalid Id
		mockMvc.perform(get("/rating/delete/{id}", 0))
			.andExpect(status().is5xxServerError());		
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_AddANewValidRating() throws Exception {
		// creates a rating to add to database
		Rating rating = new Rating("Moodys Rating Test", "Sand PRating Test", "Fitch Rating Test", 10);
		
		// tries to add the rating to database
		mockMvc
			.perform(post("/rating/validate")
				.param("moodysRating", rating.getMoodysRating())
				.param("sandPRating", rating.getSandPRating())
				.param("fitchRating", rating.getFitchRating())
				.param("orderNumber", rating.getOrderNumber().toString()))
			.andExpect(model().hasNoErrors())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/rating/list"));
		
		// retrieves the rating from database
		Iterable<Rating> ratingsIterable = new ArrayList<>();
		ratingsIterable = ratingService.findAllRatings();
		List<Rating> ratings = new ArrayList<>();
		ratingsIterable.forEach(r -> ratings.add(r));
		rating = ratings.get(ratings.size() - 1);
				
		// deletes the rating from database
		ratingService.deleteRating(rating);
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotAddANewInvalidRating() throws Exception {
		// creates an invalid rating to add to database
		Rating rating = new Rating("Moodys Rating Test", "Sand PRating Test", "Fitch Rating Test", 10);
		
		// tries to add the rating to database
		mockMvc
			.perform(post("/rating/validate")
				.param("moodysRating", "")
				.param("sandPRating", rating.getSandPRating())
				.param("fitchRating", rating.getFitchRating())
				.param("orderNumber", rating.getOrderNumber().toString()))
			.andExpect(model().hasErrors())
			.andExpect(view().name("rating/add"));
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_UpdateValidRating() throws Exception {
		// creates a rating to update
		Rating rating = new Rating("Moodys Rating Test", "Sand PRating Test", "Fitch Rating Test", 10);
		rating = ratingService.saveRating(rating);
		
		// tries to update the rating
		mockMvc
			.perform(post("/rating/update/{id}", rating.getId())
				.param("moodysRating", rating.getMoodysRating())
				.param("sandPRating", rating.getSandPRating())
				.param("fitchRating", rating.getFitchRating())
				.param("orderNumber", "20"))
			.andExpect(model().hasNoErrors())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/rating/list"));
		
		// deletes the rating 
		ratingService.deleteRating(rating);
	}
	
	@Test
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotUpdateInvalidRating() throws Exception {
		// creates a rating to update
		Rating rating = new Rating("Moodys Rating Test", "Sand PRating Test", "Fitch Rating Test", 10);
		rating = ratingService.saveRating(rating);
		
		// tries to update the rating with invalid order number
		mockMvc
			.perform(post("/rating/update/{id}", rating.getId())
				.param("moodysRating", rating.getMoodysRating())
				.param("sandPRating", rating.getSandPRating())
				.param("fitchRating", rating.getFitchRating())
				.param("orderNumber", "0"))
			.andExpect(model().hasErrors())
			.andExpect(view().name("/rating/update"));
		
		// deletes the rating to update
		ratingService.deleteRating(rating);
	}
	
	@Test(expected = NestedServletException.class)
	@WithMockUser(username = "userTest", password = "Passw0rd!", authorities = "USER")
	public void test_NotUpdateRating_withInvalidId() throws Exception {
		// tries to update a rating with invalid Id
		mockMvc
			.perform(get("/rating/update/{id}", 0))
			.andExpect(status().is5xxServerError());
	}
}
