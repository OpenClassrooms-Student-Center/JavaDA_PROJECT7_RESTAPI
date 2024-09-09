package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.RequestedObjectNotFoundException;
import com.nnk.springboot.services.RuleNameService;

import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class RuleTests {

	@Autowired
	private RuleNameService ruleNameService;

	@Test
	@Transactional
	public void ruleTest() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

		// Save
		rule = ruleNameService.save(rule);
		assertNotNull(rule.getRulenameid());
		assertTrue(rule.getName().equals("Rule Name"));

		// Update
		rule.setName("Rule Name Update");
		rule = ruleNameService.save(rule);
		assertTrue(rule.getName().equals("Rule Name Update"));

		// Find
		List<RuleName> listResult = ruleNameService.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rule.getRulenameid();
		ruleNameService.delete(id);
		RuleName ruleList;
		try {
			ruleList = ruleNameService.findById(id);
		} catch (RequestedObjectNotFoundException e) {
			ruleList = null;
		} 
		assertNull(ruleList);
	}
}
