package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;

import java.util.ArrayList;
import java.util.List;

public abstract class TestVariables {

    protected Rating rating;
    protected List<Rating> ratingList;

    public void initializeVariables() {
        rating = new Rating("moodysRating", "sandPRating", "fitchRating", 10);
        ratingList = new ArrayList<>(List.of(rating));

    }

}
