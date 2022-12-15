package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.IRatingService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * contain all business service methods for RatingService
 */
@Service
public class RatingServiceImpl implements IRatingService {

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rating findById(Integer id) throws DataNotFoundException {
        return ratingRepository.findById(id).orElseThrow(() -> new DataNotFoundException(" No User with id " + id + " found "));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Rating rating) {
        ratingRepository.save(rating);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Rating rating) {
    ratingRepository.delete(rating);
    }
}
