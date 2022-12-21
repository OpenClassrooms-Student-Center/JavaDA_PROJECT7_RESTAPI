package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.IRatingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * contain all business service methods for RatingService
 */
@Service
public class RatingServiceImpl implements IRatingService {

    /**
     * SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger logger = LogManager.getLogger("RatingServiceImpl");

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
    public void update(Rating rating) throws UsernameNotFoundException {
        logger.debug("update rating:{}", rating.getId());
        Optional<Rating> isAlreadyRating = ratingRepository.findById(rating.getId().intValue());
        if (isAlreadyRating.isPresent()) {
            ratingRepository.save(rating);
        } else {
            throw new UsernameNotFoundException("No rating " + rating + " present in dataBase ");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Rating rating) {
        logger.debug("delete rating:{}", rating.getId());
        ratingRepository.delete(rating);
    }
}
