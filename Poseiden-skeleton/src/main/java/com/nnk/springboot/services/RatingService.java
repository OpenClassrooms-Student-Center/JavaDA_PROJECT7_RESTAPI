package com.nnk.springboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RatingService {

	@Autowired
	private RatingRepository ratingRepository;
	
	public Iterable<Rating> findAllRatings() {
		log.info("All Rating retrieved from database");
		return ratingRepository.findAll();		
	}
	
	public Optional<Rating> findRatingById(Integer id) {
		log.info("Rating with id: " + id + " retrieved from database");
		return ratingRepository.findById(id);
	}
	
	public Rating saveRating(Rating rating) {
		log.info("Rating: " + rating.toString() + " saved in database");
		return ratingRepository.save(rating);
	}
	
	public void deleteRating(Rating rating) {
		log.info("Rating: " + rating.toString() + " deleted in database");
		ratingRepository.delete(rating);
	} 
}
