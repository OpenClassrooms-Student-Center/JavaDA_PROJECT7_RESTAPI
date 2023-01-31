package com.nnk.springboot.service;



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
    private RatingService ratingService;

    @Test
    public void ratingTest() {
        Rating rating = new Rating();
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(10);

        // Save
        rating = ratingService.save(rating);
        Assert.assertNotNull(rating.getId());
        Assert.assertTrue(rating.getOrderNumber() == 10);

        // Update
        rating.setOrderNumber(20);
        rating = ratingService.save(rating);
        Assert.assertTrue(rating.getOrderNumber() == 20);

        // Find
        List<Rating> listResult = ratingService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = rating.getId();
        ratingService.delete(rating);
        Optional<Rating> ratingList = ratingService.findById(id);
        Assert.assertFalse(ratingList.isPresent());
    }
}

