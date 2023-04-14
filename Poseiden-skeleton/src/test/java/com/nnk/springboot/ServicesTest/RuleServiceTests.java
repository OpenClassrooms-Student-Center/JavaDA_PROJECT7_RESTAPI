package com.nnk.springboot.ServicesTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.RuleNameService;
import org.junit.Assert;
//import org.junit.Test; CET IMPORT FAIT ECHOUER LES TESTS!!!!
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
@ExtendWith(SpringExtension.class)

public class RuleServiceTests {

	@Mock
	private RuleNameRepository ruleNameRepository;

	RuleNameService ruleNameService;

	RuleName ruleName = new RuleName();

	@BeforeEach
	public void setUp(){
		MockitoAnnotations.openMocks(this);
		ruleName.setRulename_id(1);
		ruleName.setDescription("description");
		ruleName.setJson("json");
		ruleName.setName("name");
		ruleName.setSql_part("sqlpart");
		ruleName.setSql_str("sqlstr");
		ruleName.setTemplate("template");

		ruleNameService = new RuleNameService(ruleNameRepository);


	}
	/*@Test
	public void ruleTest() {
		RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

		// Save
		rule = ruleNameRepository.save(rule);
		Assert.assertNotNull(rule.getRulename_id());
		Assert.assertTrue(rule.getName().equals("Rule Name"));

		// Update
		rule.setName("Rule Name Update");
		rule = ruleNameRepository.save(rule);
		Assert.assertTrue(rule.getName().equals("Rule Name Update"));

		// Find
		List<RuleName> listResult = ruleNameRepository.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rule.getRulename_id();
		ruleNameRepository.delete(rule);
		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
		Assert.assertFalse(ruleList.isPresent());
	}*/
	@Test
	public void findByIdTest() throws Exception {
		when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(ruleName));

		assertEquals(ruleName,ruleNameService.getRuleNameById(1));
	}

	@Test
	public void findAllTest(){
		//ARRANGE
		List<RuleName> listOfRuleNames = new ArrayList<>();
		when(ruleNameRepository.findAll()).thenReturn(listOfRuleNames);
		//ACT and ASSERT
		assertEquals(listOfRuleNames, ruleNameService.findAll());
	}
	@Test
	public void validateNewBidListTest() throws Exception {
		//ARRANGE
		when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);
		//ACT
		RuleName registeredRuleName = ruleNameService.validateNewRuleName(ruleName);
		//ASSERT
		assertNotNull(registeredRuleName);
		assertEquals("description", registeredRuleName.getDescription());
		assertEquals("json", registeredRuleName.getJson());
		assertEquals("name", registeredRuleName.getName());
		assertEquals("sqlstr", registeredRuleName.getSql_str());
		assertEquals("sqlpart", registeredRuleName.getSql_part());
		assertEquals("template", registeredRuleName.getTemplate());

		verify(ruleNameRepository, times(1)).save(registeredRuleName);
	}
	@Test
	public void updateBidListTest() throws Exception {
		//ARRANGE

		RuleName updatedRuleNameEntity = new RuleName("updatedName", "updatedDescription", "updatedJson", "updatedTemplate", "updatedSqlStr", "updatedSqlPart");
		when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
		when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
		//ACT
		RuleName result = ruleNameService.updateRuleName(ruleName.getRulename_id(), updatedRuleNameEntity);
		//ASSERT
		assertEquals(ruleName.getDescription(), result.getDescription());
		assertEquals(ruleName.getJson(), result.getJson());
		assertEquals(ruleName.getName(), result.getName());
		assertEquals(ruleName.getSql_part(), result.getSql_part());
		assertEquals(ruleName.getSql_str(), result.getSql_str());
		assertEquals(ruleName.getTemplate(), result.getTemplate());



		assertEquals(1, result.getRulename_id());

	}
	@Test
	public void deleteBidListTest() throws Exception {
		//ARRANGE
		when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
		doNothing().when(ruleNameRepository).delete(ruleName);
		//ACT
		ruleNameService.deleteRuleName(1);
		//ASSERT
		verify(ruleNameRepository, times(1)).delete(ruleName);
	}
}
