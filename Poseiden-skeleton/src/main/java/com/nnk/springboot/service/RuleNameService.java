package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    public List<RuleName> findAll(){
        return ruleNameRepository.findAll();
    }

    public RuleName save(RuleName ruleName){
        return ruleNameRepository.save(ruleName);
    }

    public Optional<RuleName> findById(Integer id) {
        return ruleNameRepository.findById(id);
    }

    public void delete(RuleName ruleName) {
        ruleNameRepository.delete(ruleName);
    }
}
