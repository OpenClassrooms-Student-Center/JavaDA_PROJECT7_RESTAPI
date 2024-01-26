package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingService {

    /**
     * Return a list of all the Rating
     * @return List of Rating
     * */
    List<Rating> findAllRating();

    /**
     * save the rating in db
     */
    void saveRating(Rating rating);

    /**
     * find the rating in db
     * @param id
     * @return
     */
    Optional<Rating> findRatingById(Integer id);

    /**
     * find the rating in db
     * @param id
     * @return
     */
    Rating findByRatingId(Integer id);

    /**
     * Check if ID exists
     * @param id
     * @return
     */
    boolean checkIfIdExists(int id);

    /**
     * delete rating in db
     * @param rating
     */
    void deleteRating(Rating rating);
}
