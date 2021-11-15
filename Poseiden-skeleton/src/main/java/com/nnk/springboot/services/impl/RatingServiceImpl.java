package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * The type Rating service.
 */
@Service
@AllArgsConstructor
public class RatingServiceImpl extends AbstractCrudService<Rating> implements RatingService {
    /**
     * The Repository.
     */
    RatingRepository repository;

    @Override
    protected JpaRepository<Rating, Integer> getRepository() {
        return repository;
    }
}
