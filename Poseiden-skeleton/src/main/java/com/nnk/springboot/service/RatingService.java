package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;

import java.util.List;

/**
 * The interface Rating service.
 */
public interface RatingService {
    /**
     * Find all Rating list.
     *
     * @return the list
     */
    List<Rating> findAll();

    /**
     * Find Rating by id.
     *
     * @param id the id
     * @return the rating
     */
    Rating findById(Integer id);

    /**
     * Create rating.
     *
     * @param ratingDto the rating dto
     * @return the rating
     */
    Rating create(RatingDto ratingDto);

    /**
     * Update rating.
     *
     * @param id        the id
     * @param ratingDto the rating dto
     * @return the rating
     */
    Rating update(Integer id, RatingDto ratingDto);

    /**
     * Delete Rating by id.
     *
     * @param id the id
     */
    void delete(Integer id);
}
