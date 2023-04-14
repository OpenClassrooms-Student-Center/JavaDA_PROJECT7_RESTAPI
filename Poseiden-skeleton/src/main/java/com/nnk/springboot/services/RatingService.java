package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");

    RatingRepository ratingRepository;
    public  RatingService(RatingRepository ratingRepository){
        this.ratingRepository = ratingRepository;
    }
    public List<Rating> findAll(){
        return ratingRepository.findAll();
    }
    public Rating getRatingById(Integer i) throws Exception{
        Optional<Rating> opt = ratingRepository.findById(i);
        return opt.get();
    }
    //CREATE NEW RATING
    public Rating validateNewRating(Rating rating) throws Exception{
        return ratingRepository.save(rating);
    }
    //UPDATE RATING
    public Rating updateRating(Integer id, Rating updatedRatingEntity) throws Exception{
        Optional<Rating> opt = ratingRepository.findById(id);
        Rating formerRating = opt.get();
        formerRating.setMoodys_rating(updatedRatingEntity.getMoodys_rating());
        formerRating.setSandprating(updatedRatingEntity.getSandprating());
        formerRating.setFitch_rating(updatedRatingEntity.getFitch_rating());
        formerRating.setOrder_number(updatedRatingEntity.getOrder_number());
        // TODO: check required fields, if valid call service to update Rating and return list Rating
        return ratingRepository.save(formerRating);

    }
    //DELETE RATING
    public void deleteRating(Integer id) throws Exception{
        Optional<Rating> opt = ratingRepository.findById(id);
        Rating ratingToDelete = opt.get();
        ratingRepository.delete(ratingToDelete);
    }

}

