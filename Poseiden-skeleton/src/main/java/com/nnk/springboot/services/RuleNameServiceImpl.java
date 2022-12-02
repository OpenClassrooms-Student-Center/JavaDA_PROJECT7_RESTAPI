package com.nnk.springboot.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

/*
 * Service for handling User related operations
 */
@Service
public class RuleNameServiceImpl implements IRuleNameService {

    private RuleNameRepository ruleNameRepository;

    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
	this.ruleNameRepository = ruleNameRepository;
    }

    @Override
    @Transactional
    public Iterable<RuleName> getRuleNames() {
	return ruleNameRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<RuleName> getRuleNameById(Integer id) {
	return ruleNameRepository.findById(id);
    }

    @Override
    @Transactional
    public RuleName saveRuleName(RuleName rulename) {
	// Assigning by default role "User" (id = 1)
	// Role defaultRole = roleService.getRoleById(1);
	// user.addRole(defaultRole);
	return ruleNameRepository.save(rulename);
    }

    @Override
    @Transactional
    public void deleteRuleNameById(Integer id) {
	ruleNameRepository.deleteById(id);
    }

}
