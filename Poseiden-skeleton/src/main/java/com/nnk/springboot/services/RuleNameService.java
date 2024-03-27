package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {
    private RuleNameRepository ruleNameRepository;
    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    public void saveRuleName(RuleName ruleName){
        ruleNameRepository.save(ruleName);
    }
    public List<RuleName> findAll(){
        return ruleNameRepository.findAll();
    }
    public RuleName findRuleNameById(Integer id){
        Optional<RuleName> ruleName = ruleNameRepository.findById(id);
        if(ruleName.isPresent()){
            return ruleName.get();
        }
        return null;
    }
    public void deleteRuleName(RuleName ruleName){
        ruleNameRepository.delete(ruleName);
    }
    public void deleteAllRuleName(){
        ruleNameRepository.deleteAll();
    }
}
