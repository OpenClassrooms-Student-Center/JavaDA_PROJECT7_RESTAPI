package com.nnk.springboot.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@Service
public class RuleNameService {
	
	@Autowired
	private RuleNameRepository ruleNameRepository;
	
	public List<RuleName> findAll() {
		return ruleNameRepository.findAll();
	}
	
	public RuleName createRuleName(RuleName ruleName) {
		return ruleNameRepository.save(ruleName);
	}
	
	public RuleName updateRuleName(RuleName ruleName, int id) throws EntityNotFoundException {
		if (ruleNameRepository.findById(id) == null) {
			throw new EntityNotFoundException("RuleName does not exists");
		}
		RuleName updatedRuleName = ruleNameRepository.getById(id);
		updatedRuleName.setName(ruleName.getName());
		updatedRuleName.setDescription(ruleName.getDescription());
		updatedRuleName.setJson(ruleName.getJson());
		updatedRuleName.setTemplate(ruleName.getTemplate());
		updatedRuleName.setSqlStr(ruleName.getSqlStr());
		updatedRuleName.setSqlPart(ruleName.getSqlPart());
		return ruleNameRepository.save(updatedRuleName);
	}
	
	public RuleName findById(int id) {
		return ruleNameRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("BidList does not exists"));
	}
	
	public void deleteById(int id) {
		ruleNameRepository.deleteById(id);
	}

}
