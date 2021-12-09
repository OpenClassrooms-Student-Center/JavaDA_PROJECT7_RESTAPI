package com.nnk.springboot.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@Service
public class RatingService {
	
	@Autowired
	private RatingRepository ratingRepository;
	
	public List<Rating> findAll() {
		return ratingRepository.findAll();
	}
	
	public Rating createRating(Rating rating) {
		return ratingRepository.save(rating);
	}
	
	public Rating updateRating(Rating rating, int id) throws EntityNotFoundException {
		if (ratingRepository.findById(id) == null) {
			throw new EntityNotFoundException("Rating does not exists");
		}
		Rating updatedRating = ratingRepository.getById(id);
		updatedRating.setMoodysRating(rating.getMoodysRating());
		updatedRating.setSandPRating(rating.getSandPRating());
		updatedRating.setFitchRating(rating.getFitchRating());
		updatedRating.setOrderNumber(rating.getOrderNumber());
		return ratingRepository.save(updatedRating);
	}
	
	public Rating findById(int id) {
		return ratingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Rating does not exists"));
	}
	
	public void deleteById(int id) {
		ratingRepository.deleteById(id);
	}

}
