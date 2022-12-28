package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.CurvePoint;
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
    public Optional<Rating> findById(Integer id) throws DataNotFoundException {
        return Optional.ofNullable(ratingRepository.findById(id).orElseThrow(() -> {
            logger.error("Invalid Rating Id: {} ", id);
            return new DataNotFoundException(" No User with id " + id + " found ");
        }));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Rating rating) {
        logger.error("save rating: {} ", rating);
        ratingRepository.save(rating);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Rating update(Rating rating) throws DataNotFoundException {
        logger.debug("update rating:{}", rating.getId());
        return ratingRepository.save(rating);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer rating) {
        logger.debug("delete rating:{}", rating);
        Rating deleteRating = ratingRepository.findById(rating).orElseThrow(() -> {
            throw new DataNotFoundException("Id " + rating + " Not Present in Data Base");
        });

        ratingRepository.deleteById(deleteRating.getId());
    }
}
