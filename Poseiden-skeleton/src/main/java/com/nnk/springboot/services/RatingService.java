package com.nnk.springboot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.RequestedObjectNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RatingService {
    /**
     * RatingRepository instance
     */
    private final RatingRepository ratingRepository;

    /**
     * Saves a rating
     * @param rating The rating to be saved
     * @return The saved rating
     */
    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    /**
     * Retrieves all ratings
     * @return A list of ratings
     */
    public List<Rating> getRatings() {
        return ratingRepository.findAll();
    }

    /**
     * Retrieves a rating by its ID
     * @param id The ID of the rating
     * @return The rating with the specified ID
     * @throws RequestedObjectNotFoundException If no rating with the specified ID is found
     */
    public Rating getRatingById(int id) throws RequestedObjectNotFoundException{
        return ratingRepository.findByRatingid(id).orElseThrow(()-> new RequestedObjectNotFoundException("Rating not found with id: " + id));
    }

    /**
     * Deletes a rating by its ID
     * @param id The ID of the rating to be deleted
     */
    @Transactional
    public void deleteRating(int id) {
        ratingRepository.deleteByRatingid(id);
    }
}

