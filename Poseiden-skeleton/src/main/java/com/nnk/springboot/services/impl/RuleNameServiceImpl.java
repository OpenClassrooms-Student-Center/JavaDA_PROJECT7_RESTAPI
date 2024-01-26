package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameServiceImpl implements RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     *{@inheritDoc}
     */
    @Override
    public List<RuleName> findAllRuleName() {
        return ruleNameRepository.findAll();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void saveRuleName(RuleName ruleName) {
        ruleNameRepository.save(ruleName);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Optional<RuleName> findRuleNameById(Integer id){
        return ruleNameRepository.findById(id);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public RuleName findByRuleNameId(Integer id){
        return ruleNameRepository.findById(id).get();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public boolean checkIfIdExists(int id) {
        return ruleNameRepository.existsById(id);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void deleteRuleName(RuleName ruleName) {
        ruleNameRepository.delete(ruleName);
    }

}
