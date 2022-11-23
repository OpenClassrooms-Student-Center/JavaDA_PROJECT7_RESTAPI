package com.nnk.springboot;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nnk.springboot.repositories.RuleNameRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleTests {

    @Autowired
    private RuleNameRepository ruleNameRepository;

//	@Test
//	public void ruleTest() {
//		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
//
//		// Save
//		rule = ruleNameRepository.save(rule);
//		Assert.assertNotNull(rule.getId());
//		Assert.assertTrue(rule.getName().equals("Rule Name"));
//
//		// Update
//		rule.setName("Rule Name Update");
//		rule = ruleNameRepository.save(rule);
//		Assert.assertTrue(rule.getName().equals("Rule Name Update"));
//
//		// Find
//		List<RuleName> listResult = ruleNameRepository.findAll();
//		Assert.assertTrue(listResult.size() > 0);
//
//		// Delete
//		Integer id = rule.getId();
//		ruleNameRepository.delete(rule);
//		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
//		Assert.assertFalse(ruleList.isPresent());
//	}
}
