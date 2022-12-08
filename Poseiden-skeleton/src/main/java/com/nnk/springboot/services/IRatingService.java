package com.nnk.springboot.services;

import java.util.Optional;

import com.nnk.springboot.domain.Rating;

public interface IRatingService {
    public Iterable<Rating> getRatings();

    public Optional<Rating> getRatingById(Integer id);

    public Rating saveRating(Rating rating);

    public void deleteRatingById(Integer id);
}
