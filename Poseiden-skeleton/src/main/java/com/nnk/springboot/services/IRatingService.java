package com.nnk.springboot.services;

import java.util.Optional;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;

public interface IRatingService {
    public Iterable<Rating> getRatings();

    public Optional<Rating> getRatingById(Integer id);

    public Rating saveRating(Rating rating);

    public void deleteRatingById(Integer id);
}
