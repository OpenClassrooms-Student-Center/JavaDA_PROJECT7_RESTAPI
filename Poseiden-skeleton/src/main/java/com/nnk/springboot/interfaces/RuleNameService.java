package com.nnk.springboot.interfaces;

import com.nnk.springboot.model.RuleName;

public interface RuleNameService {
    void validateRuleName(RuleName ruleName);

    void updateRuleName(Integer id, RuleName ruleName);

    void deleteRuleName(Integer id);
}
