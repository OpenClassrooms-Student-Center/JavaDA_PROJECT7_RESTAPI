package com.nnk.springboot.unit.repositories;

import com.nnk.springboot.domain.Rule;
import com.nnk.springboot.repositories.RuleRepository;
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
	private RuleRepository RuleRepository;

	@Test
	public void ruleTest() {
		Rule rule = new Rule("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

		// Save
		rule = RuleRepository.save(rule);
		Assert.assertNotNull(rule.getId());
		Assert.assertTrue(rule.getName().equals("Rule Name"));

		// Update
		rule.setName("Rule Name Update");
		rule = RuleRepository.save(rule);
		Assert.assertTrue(rule.getName().equals("Rule Name Update"));

		// Find
		List<Rule> listResult = RuleRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rule.getId();
		RuleRepository.delete(rule);
		Optional<Rule> ruleList = RuleRepository.findById(id);
		Assert.assertFalse(ruleList.isPresent());
	}
}
