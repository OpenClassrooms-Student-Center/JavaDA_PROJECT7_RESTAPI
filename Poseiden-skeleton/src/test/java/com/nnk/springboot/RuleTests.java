package com.nnk.springboot;

import com.nnk.springboot.interfaces.RuleNameService;
import com.nnk.springboot.model.Rating;
import com.nnk.springboot.model.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleTests {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Autowired
	private RuleNameService ruleNameService;

	@Test
	public void ruleTest() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

		// Save
		rule = ruleNameRepository.save(rule);
		Assert.assertNotNull(rule.getId());
		Assert.assertTrue(rule.getName().equals("Rule Name"));

		// Update
		rule.setName("Rule Name Update");
		rule = ruleNameRepository.save(rule);
		Assert.assertTrue(rule.getName().equals("Rule Name Update"));

		// Find
		List<RuleName> listResult = ruleNameRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rule.getId();
		ruleNameRepository.delete(rule);
		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
		Assert.assertFalse(ruleList.isPresent());
	}
	@Test
	public void deleteRuleNameTest() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		rule = ruleNameRepository.save(rule);
		// Delete
		Integer id = rule.getId();
		ruleNameService.deleteRuleName(id);
		Optional<RuleName> ruleName = ruleNameRepository.findById(id);
		Assert.assertFalse(ruleName.isPresent());
	}
	@Test
	public void updateRuleTest() {
		//given
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
		rule = ruleNameRepository.save(rule);
		Integer id = rule.getId();

		RuleName ruleName = new RuleName("Test", "test", "test", "test", "test", "test");

		//when
		ruleNameService.updateRuleName(id, ruleName);

		//then
		Assert.assertEquals(rule.getName(),"Test", "Test");
	}

	@Test
	public void validateRuleTest() {
		//given
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

		//when
		ruleNameService.validateRuleName(rule);

		//then
		RuleName ruleNameId = ruleNameRepository.findIdByNameAndDescription("Rule Name", "Description");
		Optional<RuleName> ruleName = ruleNameRepository.findById(ruleNameId.getId());
		Assert.assertTrue(ruleName.isPresent());
	}
}
