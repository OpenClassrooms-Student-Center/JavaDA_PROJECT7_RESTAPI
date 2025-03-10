package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingTests {

	@Autowired
	private RatingRepository ratingRepository;

	@Test
	public void ratingTest() {
		Rating rating = new Rating();
		rating.setOrderNumber(10);
		rating.setMoodysRating("Moodys Rating");
		rating.setFitchRating("Fitch Rating");
		rating.setSandPRating("Sand PRating");

		// Save
		rating = ratingRepository.save(rating);
		Integer generatedId = rating.getId();

		Assert.assertNotNull(generatedId);
		Assert.assertTrue(generatedId > 0);
		Assert.assertEquals(Integer.valueOf(10), rating.getOrderNumber());

		// Update
		rating.setOrderNumber(20);
		Rating updatedRating = ratingRepository.save(rating);
		Assert.assertEquals(Integer.valueOf(20), updatedRating.getOrderNumber());

		// Find
		List<Rating> listResult = ratingRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// FindById
		Optional<Rating> foundRating = ratingRepository.findById(generatedId);
		Assert.assertTrue(foundRating.isPresent());

		// Delete
		ratingRepository.delete(rating);
		Optional<Rating> deletedRating = ratingRepository.findById(generatedId);
		Assert.assertFalse(deletedRating.isPresent());
	}
}