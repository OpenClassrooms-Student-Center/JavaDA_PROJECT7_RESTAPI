package com.nnk.springboot.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@SpringBootTest
public class RuleNameServiceTest {

    private IRuleNameService ruleNameService;

    private static RuleName ruleNameToAdd = new RuleName();

    @Mock
    private RuleNameRepository ruleNameRepository;

    @BeforeEach
    public void setUp() {
	MockitoAnnotations.openMocks(this);

	ruleNameService = new RuleNameServiceImpl(ruleNameRepository);
	ruleNameToAdd.setRuleNameId(0);
    }

    @Test
    public void shouldGetAllRuleNames() {
	List<RuleName> ruleNameList = new ArrayList<>();
	ruleNameList.add(ruleNameToAdd);

	when(ruleNameRepository.findAll()).thenReturn(ruleNameList);

	List<RuleName> ruleNames = (List<RuleName>) ruleNameService.getRuleNames();
	assertEquals(ruleNames.get(0).getRuleNameId(), ruleNameToAdd.getRuleNameId());
    }

    @Test
    public void shouldGetOneRuleName() {
	when(ruleNameRepository.findById(ruleNameToAdd.getRuleNameId())).thenReturn(Optional.of(ruleNameToAdd));

	Optional<RuleName> ruleName = ruleNameService.getRuleNameById(ruleNameToAdd.getRuleNameId());

	assertEquals(ruleName.get().getRuleNameId(), ruleNameToAdd.getRuleNameId());
    }

    @Test
    public void shouldSaveRuleName() {
	when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleNameToAdd);

	RuleName ruleNameUser = ruleNameService.saveRuleName(ruleNameToAdd);

	assertEquals(ruleNameUser.getRuleNameId(), ruleNameToAdd.getRuleNameId());
    }

    @Test
    public void shouldDeleteRuleName() {
	ruleNameService.deleteRuleNameById(ruleNameToAdd.getRuleNameId());

	verify(ruleNameRepository, times(1)).deleteById(ruleNameToAdd.getRuleNameId());
    }

}
