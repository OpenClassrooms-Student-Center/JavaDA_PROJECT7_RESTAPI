package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class RatingService implements IRatingService{
    private final RatingRepository ratingRepository;

    @Override
    public Rating findById(Integer ratingId) {
        Optional<Rating> optionalRating = ratingRepository.findById(ratingId);
        if(optionalRating.isPresent()) {
            return optionalRating.get();
        }
        return null;
    }

    @Override
    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating updateRating(Rating rating) {
        return null;
    }

    @Override
    public boolean deleteRating(Rating rating) {
        ratingRepository.delete(rating);
        return findById(rating.getId()) == null;
    }
}
