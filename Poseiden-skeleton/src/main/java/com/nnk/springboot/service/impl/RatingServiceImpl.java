package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;
import com.nnk.springboot.util.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class that handles all Rating related business logic.
 */
@Service
@Slf4j
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    /**
     * Instantiates a new Rating service.
     *
     * @param ratingRepository the rating repository
     */
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> findAll() {
        log.info("Retrieving all Ratings");
        return ratingRepository.findAll();
    }

    @Override
    public Rating findById(Integer id) {
        log.info("Retrieving Rating with id {}", id);
        Optional<Rating> rating = ratingRepository.findById(id);
        return rating.orElseThrow(() -> new NotFoundException("Rating not found with id " + id));
    }

    @Override
    public Rating create(RatingDto ratingDto) {
        log.info("Creating new Rating");
        Rating rating = new Rating(ratingDto);
        return ratingRepository.save(rating);
    }

    @Override
    public Rating update(Integer id, RatingDto ratingDto) {
        log.info("Updating Rating with id {}", id);
        Rating rating = findById(id);
        rating.setMoodysRating(ratingDto.getMoodysRating());
        rating.setSandPRating(ratingDto.getSandPRating());
        rating.setFitchRating(ratingDto.getFitchRating());
        rating.setOrderNumber(ratingDto.getOrderNumber());
        return ratingRepository.save(rating);
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting Rating with id {}", id);
        Rating rating = findById(id);
        ratingRepository.delete(rating);
    }
}
