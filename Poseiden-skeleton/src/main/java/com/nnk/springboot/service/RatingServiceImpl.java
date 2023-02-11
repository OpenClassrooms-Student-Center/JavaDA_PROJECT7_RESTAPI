package com.nnk.springboot.service;

import com.nnk.springboot.NotFoundException;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating findById(Integer id) {
        Optional<Rating> rating = ratingRepository.findById(id);
        return rating.orElseThrow(() -> new NotFoundException("Rating not found with id " + id));
    }

    @Override
    public Rating create(RatingDto ratingDto) {
        Rating rating = new Rating(ratingDto);
        return ratingRepository.save(rating);
    }

    @Override
    public Rating update(Integer id, RatingDto ratingDto) {
        Rating rating = findById(id);
        rating.setMoodysRating(ratingDto.getMoodysRating());
        rating.setSandPRating(ratingDto.getSandPRating());
        rating.setFitchRating(ratingDto.getFitchRating());
        rating.setOrderNumber(ratingDto.getOrderNumber());
        return ratingRepository.save(rating);
    }

    @Override
    public void delete(Integer id) {
        Rating rating = findById(id);
        ratingRepository.delete(rating);
    }
}
