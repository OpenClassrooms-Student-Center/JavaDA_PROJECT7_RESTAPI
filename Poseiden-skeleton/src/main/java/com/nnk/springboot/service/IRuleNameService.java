package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;

public interface IRuleNameService {
    RuleName findById(Integer ruleNameId);
    RuleName saveRuleName(RuleName ruleName);
    RuleName updateRuleName(RuleName ruleName);
    boolean deleteRuleName(RuleName ruleName);
}
