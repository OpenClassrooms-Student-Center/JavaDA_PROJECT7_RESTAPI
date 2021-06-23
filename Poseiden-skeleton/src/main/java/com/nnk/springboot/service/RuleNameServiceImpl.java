package com.nnk.springboot.service;

import com.nnk.springboot.interfaces.RuleNameService;
import com.nnk.springboot.model.CurvePoint;
import com.nnk.springboot.model.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RuleNameServiceImpl implements RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    @Override
    public void validateRuleName(RuleName ruleName){

        RuleName addRuleName = new RuleName();
        addRuleName.setName(ruleName.getName());
        addRuleName.setDescription(ruleName.getDescription());
        addRuleName.setJson(ruleName.getJson());
        addRuleName.setTemplate(ruleName.getTemplate());
        addRuleName.setSqlStr(ruleName.getSqlStr());
        addRuleName.setSqlPart(ruleName.getSqlPart());
        ruleNameRepository.save(addRuleName);
    }

    @Override
    public void updateRuleName(Integer id, RuleName ruleName) {

        ruleName.setId(id);
        ruleNameRepository.save(ruleName);
    }

    @Override
    public void deleteRuleName(Integer id) {
        RuleName ruleName = ruleNameRepository.findRuleNameById(id);
        ruleNameRepository.delete(ruleName);
    }
}
