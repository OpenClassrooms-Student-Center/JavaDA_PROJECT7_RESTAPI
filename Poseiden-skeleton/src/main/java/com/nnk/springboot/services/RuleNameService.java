package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.RequestedObjectNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * A service class for the RuleName domain.
 * 
 */
@Service
@RequiredArgsConstructor
public class RuleNameService {
    /**
     * The ruleName repository.
     */
    @Autowired
    private final RuleNameRepository ruleNameRepository;

    /**
     * Save a ruleName to the repository.
     * 
     * @param ruleName the ruleName to save
     * @return the saved ruleName
     */
    public RuleName save(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Retrieve all ruleNames from the repository.
     * 
     * @return a list of ruleNames
     */
    public List<RuleName> findAll() {
        return ruleNameRepository.findAll();
    }

    /**
     * Retrieve a ruleName by its id from the repository.
     * 
     * @param id the id of the ruleName
     * @return the ruleName with the given id
     * @throws RequestedObjectNotFoundException if no ruleName is found with the given id
     */
    public RuleName findById(Integer id) throws RequestedObjectNotFoundException {
        return ruleNameRepository.findByRulenameid(id).orElseThrow(()-> new RequestedObjectNotFoundException("RuleName not found with id: " + id));
    }
    
    /**
     * Delete a ruleName from the repository.
     * 
     * @param ruleName the id of the ruleName to delete
     */
    @Transactional
    public void delete(int ruleName) {
        ruleNameRepository.deleteByRulenameid(ruleName);
    }
}

