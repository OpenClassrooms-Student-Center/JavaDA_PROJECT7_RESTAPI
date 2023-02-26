package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;

public interface IRatingService {
    Rating findById(Integer ratingId);
    Rating saveRating(Rating rating);
    Rating updateRating(Rating rating);
    boolean deleteRating(Rating rating);
}
