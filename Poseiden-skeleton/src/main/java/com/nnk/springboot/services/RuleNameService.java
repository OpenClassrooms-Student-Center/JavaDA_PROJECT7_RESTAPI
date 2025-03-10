package com.nnk.springboot.services;

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

    public List<RuleName> getAllRuleNames(){
        return ruleNameRepository.findAll();
    }

    public Optional<RuleName> getRuleNameById(Integer id){
        return ruleNameRepository.findById(id);
    }

    public RuleName saveRuleName(RuleName ruleName){
        return ruleNameRepository.save(ruleName);
    }

    public void deleteRuleNameById(Integer id){
        ruleNameRepository.deleteById(id);
    }
}
