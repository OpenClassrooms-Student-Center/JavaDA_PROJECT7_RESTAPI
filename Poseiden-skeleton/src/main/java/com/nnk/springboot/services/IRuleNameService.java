package com.nnk.springboot.services;

import java.util.Optional;

import com.nnk.springboot.domain.Role;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;

public interface IRuleNameService {
    public Iterable<RuleName> getRuleNames();

    public Optional<RuleName> getRuleNameById(Integer id);

    public RuleName saveRuleName(RuleName ruleName);

    public void deleteRuleNameById(Integer id);
}
