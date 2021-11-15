package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.repositories.RuleRepository;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.services.RuleService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * The type Rule service.
 */
@Service
@AllArgsConstructor
public class RuleServiceImpl extends AbstractCrudService<Rule> implements RuleService {
    /**
     * The Repository.
     */
    RuleRepository repository;

    @Override
    protected JpaRepository<Rule, Integer> getRepository() {
        return repository;
    }
}
