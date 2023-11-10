package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class TestVariables {

    protected Rating rating;
    protected List<Rating> ratingList;
    protected Optional<Rating> ratingOptional;

    protected String longString;

    protected String ratingFitchRatingSize;
    protected String ratingMoodysRatingSize;
    protected String ratingOrderMin;
    protected String ratingOrderMax;
    protected String ratingSandPRatingSize;

    protected Integer ratingId; // id of the rating created for integration tests

    public void initializeVariables() {
        rating = new Rating("moodysRatingTestValue", "sandPRatingTestValue", "fitchRatingTestValue", 10);
        ratingList = new ArrayList<>(List.of(rating));
        ratingOptional = Optional.of(rating);

        // 126 characters string used for size validation
        longString = "85368220096847824275049423209683" +
                "35786401076532718070358521773728" +
                "25842050212176069074456918678132" +
                "635754452771884752599433457836";

        ratingFitchRatingSize = "fitchRating should be less than 126 characters";
        ratingMoodysRatingSize = "moodysRating should be less than 126 characters";
        ratingOrderMin = "order should be more than -129";
        ratingOrderMax = "order should be less than 128";
        ratingSandPRatingSize = "sandPRating should be less than 126 characters";
    }

}
