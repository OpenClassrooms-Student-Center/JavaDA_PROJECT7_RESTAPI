package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDto;

import java.util.List;

public interface RatingService {
    List<Rating> findAll();

    Rating findById(Integer id);

    Rating create(RatingDto ratingDto);

    Rating update(Integer id, RatingDto ratingDto);

    void delete(Integer id);
}
