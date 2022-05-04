package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
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

//	@Test
//	public void ratingTest() {
//		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
//
//		// Save
//		rating = ratingRepository.save(rating);
//		Assert.assertNotNull(rating.getId());
//		Assert.assertTrue(rating.getOrderNumber() == 10);
//
//		// Update
//		rating.setOrderNumber(20);
//		rating = ratingRepository.save(rating);
//		Assert.assertTrue(rating.getOrderNumber() == 20);
//
//		// Find
//		List<Rating> listResult = ratingRepository.findAll();
//		Assert.assertTrue(listResult.size() > 0);
//
//		// Delete
//		Integer id = rating.getId();
//		ratingRepository.delete(rating);
//		Optional<Rating> ratingList = ratingRepository.findById(id);
//		Assert.assertFalse(ratingList.isPresent());
//	}
}
