package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class RuleNameService implements IRuleNameService {
    private final RuleNameRepository ruleNameRepository;
    @Override
    public RuleName findById(Integer ruleNameId) {
        Optional<RuleName> optionalRuleName = ruleNameRepository.findById(ruleNameId);
        if(optionalRuleName.isPresent()) {
            return optionalRuleName.get();
        }
        return null;
    }

    @Override
    public RuleName saveRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public RuleName updateRuleName(RuleName ruleName) {
        return null;
    }

    @Override
    public boolean deleteRuleName(RuleName ruleName) {
        ruleNameRepository.delete(ruleName);
        return findById(ruleName.getId()) == null;
    }
}
