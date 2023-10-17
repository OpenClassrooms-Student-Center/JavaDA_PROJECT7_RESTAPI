package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    /*public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }*/

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    public Optional<Rating> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        return ratingRepository.findById(id);
    }

    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        ratingRepository.deleteById(id);
    }

    public void save(Rating rating) {
        ratingRepository.save(rating);
    }
}
