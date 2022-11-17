package com.nnk.springboot.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.repositories.RoleRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.repositories.UserRepository;

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
	//Role defaultRole = roleService.getRoleById(1);
	//user.addRole(defaultRole);
	return ratingRepository.save(rating);
    }

    @Override
    @Transactional
    public void deleteRatingById(Integer id) {
    	ratingRepository.deleteById(id);
    }

}
