package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = RuleName.class, idClass = Integer.class)
public interface RuleNameRepository {
    RuleName save(RuleName ruleName);
    List<RuleName> findAll();
    Optional<RuleName> findByRulenameid(Integer id);
    void deleteByRulenameid(int ruleName);
}
