package com.nnk.springboot.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

/*
 * Service for handling User related operations
 */
@Service
public class RatingServiceImpl implements IRatingService {

    private RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
	this.ratingRepository = ratingRepository;
    }

    @Override
    @Transactional
    public Iterable<Rating> getRatings() {
	return ratingRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Rating> getRatingById(Integer id) {
	return ratingRepository.findById(id);
    }

    @Override
    @Transactional
    public Rating saveRating(Rating rating) {
	// Assigning by default role "User" (id = 1)
	// Role defaultRole = roleService.getRoleById(1);
	// user.addRole(defaultRole);
	return ratingRepository.save(rating);
    }

    @Override
    @Transactional
    public void deleteRatingById(Integer id) {
	ratingRepository.deleteById(id);
    }

}
