package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleTests {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Test
	public void ruleTest() {
		RuleName rule = new RuleName();
		rule.setName("Rule Name");
		rule.setDescription("Description");
		rule.setJson("Json");
		rule.setTemplate("Template");
		rule.setSqlStr("SQL");
		rule.setSqlPart("SQL Part");

		// Save
		rule = ruleNameRepository.save(rule);
		Integer generatedId = rule.getId();

		Assert.assertNotNull(generatedId);
		Assert.assertTrue(generatedId > 0);
		Assert.assertEquals("Rule Name", rule.getName());

		// Update
		rule.setName("Rule Name Update");
		RuleName updatedRule = ruleNameRepository.save(rule);
		Assert.assertEquals("Rule Name Update", updatedRule.getName());

		// Find
		List<RuleName> listResult = ruleNameRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// FindById
		Optional<RuleName> foundRule = ruleNameRepository.findById(generatedId);
		Assert.assertTrue(foundRule.isPresent());

		// Delete
		ruleNameRepository.delete(rule);
		Optional<RuleName> deletedRule = ruleNameRepository.findById(generatedId);
		Assert.assertFalse(deletedRule.isPresent());
	}
}