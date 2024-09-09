package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.RequestedObjectNotFoundException;
import com.nnk.springboot.services.RatingService;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
public class RatingTests {

	@Autowired
	private RatingService ratingRsService;

	@Test
	@Transactional
	public void ratingTest() {
		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

		// Save
		rating = ratingRsService.saveRating(rating);
		assertNotNull(rating.getRatingid());
		assertTrue(rating.getOrderNumber() == 10);

		// Update
		rating.setOrderNumber(20);
		rating = ratingRsService.saveRating(rating);
		assertTrue(rating.getOrderNumber() == 20);

		// Find
		List<Rating> listResult = ratingRsService.getRatings();
		assertTrue(listResult.size() > 0);

		// Delete
		int id = rating.getRatingid();
		ratingRsService.deleteRating(id);
		Rating ratingList;
		try {
			ratingList = ratingRsService.getRatingById(id);
		} catch (RequestedObjectNotFoundException e) {
			ratingList = null;
		}
		assertNull(ratingList);
	}
}
