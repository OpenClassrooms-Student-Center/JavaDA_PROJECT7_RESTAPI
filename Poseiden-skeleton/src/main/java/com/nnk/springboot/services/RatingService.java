package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    private RatingRepository ratingRepository;
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public void saveRating(Rating rating){
        ratingRepository.save(rating);
    }
    public List<Rating> findAll(){
        return ratingRepository.findAll();
    }
    public Rating findRatingById(Integer id){
        Optional<Rating> rating = ratingRepository.findById(id);
        if(rating.isPresent()){
            return rating.get();
        }
        return null;
    }
    public void deleteRating(Rating rating){
        ratingRepository.delete(rating);
    }
    public void deleteAllRating(){ratingRepository.deleteAll();}
}
