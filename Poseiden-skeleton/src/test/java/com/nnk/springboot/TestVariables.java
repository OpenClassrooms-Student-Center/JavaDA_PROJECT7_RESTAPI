package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class TestVariables {

    protected Rating rating;
    protected List<Rating> ratingList;
    protected Optional<Rating> ratingOptional;

    public void initializeVariables() {
        rating = new Rating("moodysRating", "sandPRating", "fitchRating", 10);
        rating.setId(1);
        ratingList = new ArrayList<>(List.of(rating));
        ratingOptional = Optional.of(rating);

    }

}
