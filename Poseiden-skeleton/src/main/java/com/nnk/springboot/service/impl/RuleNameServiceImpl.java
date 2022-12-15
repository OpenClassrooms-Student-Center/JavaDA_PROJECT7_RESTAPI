package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.IRuleNameService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * contain all business service methods for RuleNameService
 */
@Service
public class RuleNameServiceImpl implements IRuleNameService {

    private final RuleNameRepository ruleNameRepository;

    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RuleName findById(Integer id) throws DataNotFoundException {
        return ruleNameRepository.findById(id).orElseThrow(() -> new DataNotFoundException("No User with id " + id + " found "));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(RuleName ruleName) {
        ruleNameRepository.save(ruleName);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(RuleName ruleName) {
        ruleNameRepository.delete(ruleName);
    }
}
