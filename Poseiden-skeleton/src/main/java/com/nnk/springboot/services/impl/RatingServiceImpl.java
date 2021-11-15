package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RatingServiceImpl extends AbstractCrudService<Rating> implements RatingService {
    RatingRepository repository;

    @Override
    protected JpaRepository<Rating, Integer> getRepository() {
        return repository;
    }
}
