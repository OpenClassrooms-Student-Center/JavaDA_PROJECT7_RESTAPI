package com.nnk.springboot.interfaces;

import com.nnk.springboot.model.Rating;

public interface RatingService {

    void updateRating(Integer id, Rating rating);

    void validateRating(Rating rating);

    void deleteRating(Integer id);

}
