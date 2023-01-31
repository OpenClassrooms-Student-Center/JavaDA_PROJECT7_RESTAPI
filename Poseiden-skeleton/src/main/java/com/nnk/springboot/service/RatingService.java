package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
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
    public List<Rating> findAll(){
        return ratingRepository.findAll();
    }

    public Rating save(Rating rating){
        return ratingRepository.save(rating);
    }

    public Optional<Rating> findById(Integer id) {
        return ratingRepository.findById(id);
    }

    public void delete(Rating rating) {
        ratingRepository.delete(rating);
    }
}
