package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDto;

import java.util.List;

public interface RuleNameService {
    List<RuleName> findAll();

    RuleName findById(Integer id);

    RuleName create(RuleNameDto ruleNameDto);

    RuleName update(Integer id, RuleNameDto ruleNameDto);

    void delete(Integer id);
}
