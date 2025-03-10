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
    private RatingRepository ratingRepository;

    public List<Rating> getAllRating(){
        return ratingRepository.findAll();
    }

    public Optional<Rating> getRatingById(Integer id){
        return ratingRepository.findById(id);
    }

    public Rating saveRating( Rating rating){
        return ratingRepository.save(rating);
    }

    public void deleteRatingById(Integer id){
        ratingRepository.deleteById(id);
    }
}
