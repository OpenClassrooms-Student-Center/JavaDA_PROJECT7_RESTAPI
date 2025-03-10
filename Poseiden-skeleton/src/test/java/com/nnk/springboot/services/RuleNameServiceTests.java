package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTests {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;

    private RuleName ruleName;

    @BeforeEach
    void setUp() {
        ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("Rule Name");
        ruleName.setDescription("Description");
        ruleName.setJson("Json");
        ruleName.setTemplate("Template");
        ruleName.setSqlStr("SQL");
        ruleName.setSqlPart("SQL Part");
    }

    @Test
    void testGetAllRuleNames() {
        when(ruleNameRepository.findAll()).thenReturn(Arrays.asList(ruleName));

        List<RuleName> result = ruleNameService.getAllRuleNames();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Rule Name", result.get(0).getName());
    }

    @Test
    void testGetRuleNameById() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        Optional<RuleName> result = ruleNameService.getRuleNameById(1);
        assertTrue(result.isPresent());
        assertEquals("Rule Name", result.get().getName());
    }

    @Test
    void testSaveRuleName() {
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);

        RuleName savedRuleName = ruleNameService.saveRuleName(ruleName);
        assertNotNull(savedRuleName);
        assertEquals("Description", savedRuleName.getDescription());
    }

    @Test
    void testDeleteRuleNameById() {
        doNothing().when(ruleNameRepository).deleteById(1);
        ruleNameService.deleteRuleNameById(1);
        verify(ruleNameRepository, times(1)).deleteById(1);
    }
}
