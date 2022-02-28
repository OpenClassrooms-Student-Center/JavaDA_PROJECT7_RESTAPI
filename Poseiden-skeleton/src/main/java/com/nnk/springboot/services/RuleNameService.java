package com.nnk.springboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RuleNameService {

	@Autowired
	private RuleNameRepository ruleNameRepository;
	
	public Iterable<RuleName> findAllRuleNames() {
		log.info("All RuleName retrieved from database");
		return ruleNameRepository.findAll();		
	}
	
	// a revoir
	public Optional<RuleName> findRuleNameById(Integer id) {
		log.info("RuleName with id: " + id + " retrieved from database");
		return ruleNameRepository.findById(id);
	}
	
	public RuleName saveRuleName(RuleName ruleName) {
		log.info("RuleName: " + ruleName.toString() + " saved in database");
		return ruleNameRepository.save(ruleName);
	}
	
	public void deleteRuleName(RuleName ruleName) {
		log.info("RuleName: " + ruleName.toString() + " deleted in database");
		ruleNameRepository.delete(ruleName);
	} 
}
