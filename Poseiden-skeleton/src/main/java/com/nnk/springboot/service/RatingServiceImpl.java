package com.nnk.springboot.service;

import com.nnk.springboot.interfaces.RatingService;
import com.nnk.springboot.model.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;


    @Override
    public void updateRating(Integer id, Rating rating) {

        rating.setId(id);
        ratingRepository.save(rating);

    }

    @Override
    public void validateRating(Rating rating){


        Rating addRating = new Rating();
        addRating.setMoodysRating(rating.getMoodysRating());
        addRating.setSandPRating(rating.getSandPRating());
        addRating.setFitchRating(rating.getFitchRating());
        addRating.setOrderNumber(rating.getOrderNumber());
        ratingRepository.save(addRating);

    }

    @Override
    public void deleteRating(Integer id) {

       Rating rating = ratingRepository.findRatingById(id);
       ratingRepository.delete(rating);

    }
}
