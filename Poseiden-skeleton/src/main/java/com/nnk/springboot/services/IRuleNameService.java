package com.nnk.springboot.services;

import java.util.Optional;

import com.nnk.springboot.domain.RuleName;

public interface IRuleNameService {
    public Iterable<RuleName> getRuleNames();

    public Optional<RuleName> getRuleNameById(Integer id);

    public RuleName saveRuleName(RuleName ruleName);

    public void deleteRuleNameById(Integer id);
}
