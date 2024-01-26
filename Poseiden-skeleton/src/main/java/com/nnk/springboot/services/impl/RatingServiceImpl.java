package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    /**
     *{@inheritDoc}
     */
    @Override
    public List<Rating> findAllRating() {
        return ratingRepository.findAll();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void saveRating(Rating rating) {
        ratingRepository.save(rating);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<Rating> findRatingById(Integer id){
        return ratingRepository.findById(id);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Rating findByRatingId(Integer id){
        return ratingRepository.findById(id).get();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public boolean checkIfIdExists(int id) {
        return ratingRepository.existsById(id);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void deleteRating(Rating rating) {
        ratingRepository.delete(rating);
    }
}
