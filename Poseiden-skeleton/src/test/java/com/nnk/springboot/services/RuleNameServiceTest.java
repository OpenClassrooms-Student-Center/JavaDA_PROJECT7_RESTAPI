package com.nnk.springboot.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.security.UserDetailsServiceImpl;

@WebMvcTest(RuleNameService.class)
class RuleNameServiceTest {
	
	@MockBean
	private RuleNameRepository ruleNameRepository;
	
	@MockBean
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private RuleNameService ruleNameService;
	
	private static RuleName ruleName = new RuleName();
	
	@BeforeEach
	private void init() {
		ruleName.setId(1);
		ruleName.setName("Name");
		ruleName.setDescription("Description");
        ruleName.setJson("Json");
        ruleName.setTemplate("Template");
        ruleName.setSqlStr("SqlStr");
        ruleName.setSqlPart("SqlPart");
	}

	@Test
	final void testFindAll() {
		List<RuleName> findAll = new ArrayList<>();
		findAll.add(ruleName);
		when(ruleNameRepository.findAll()).thenReturn(findAll);
		List<RuleName> foundList = ruleNameService.findAll();
		assertThat(foundList).isEqualTo(findAll);
	}

	@Test
	final void testCreateRuleName() {
		RuleName ruleName = new RuleName();
		ruleName.setId(2);
		ruleName.setName("Name");
		ruleName.setDescription("Description");
	    ruleName.setJson("Json");
	    ruleName.setTemplate("Template");
	    ruleName.setSqlStr("SqlStr");
	    ruleName.setSqlPart("SqlPart");
	    when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
	    ruleNameService.createRuleName(ruleName);
	    verify(ruleNameRepository).save(ruleName);
	}

	@Test
	final void testUpdateRuleName() {
		RuleName toUpdateruleName = new RuleName();
		toUpdateruleName.setId(2);
		toUpdateruleName.setName("Test");
		toUpdateruleName.setDescription("Test");
		toUpdateruleName.setJson("Test");
		toUpdateruleName.setTemplate("Test");
		toUpdateruleName.setSqlStr("Test");
		toUpdateruleName.setSqlPart("Test");
	    when(ruleNameRepository.findById(1)).thenReturn(Optional.of(RuleNameServiceTest.ruleName));
		when(ruleNameRepository.getById(1)).thenReturn(RuleNameServiceTest.ruleName);
		when(ruleNameRepository.save(toUpdateruleName)).thenReturn(toUpdateruleName);
		ruleNameService.updateRuleName(toUpdateruleName, 1);
		assertThat(ruleName.getName()).isEqualTo("Test");
		assertThat(ruleName.getDescription()).isEqualTo("Test");
		assertThat(ruleName.getJson()).isEqualTo("Test");
		assertThat(ruleName.getTemplate()).isEqualTo("Test");
		assertThat(ruleName.getSqlStr()).isEqualTo("Test");
		assertThat(ruleName.getSqlPart()).isEqualTo("Test");
	}
	
	@Test
	final void testUpdateRuleNameThrowEntityNotFoundException() {
		when(ruleNameRepository.findById(1)).thenReturn(null);
		assertThrows(EntityNotFoundException.class, () -> ruleNameService.updateRuleName(ruleName, 1));
	}

	@Test
	final void testFindById() {
		when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
		RuleName foundRuleName = ruleNameService.findById(1);
		assertThat(foundRuleName).isEqualTo(ruleName);
	}
	
	@Test
	final void testFindByIdNotFound() throws EntityNotFoundException {
		assertThrows(EntityNotFoundException.class, () -> ruleNameService.findById(0));
	}

	@Test
	final void testDeleteById() {
		ruleNameService.deleteById(1);
		verify(ruleNameRepository).deleteById(1);
	}

}
