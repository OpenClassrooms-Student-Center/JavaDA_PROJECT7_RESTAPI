package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDto;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
import com.nnk.springboot.util.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(RuleNameServiceImpl.class)
class RuleNameServiceImplTest {

    private static final RuleName ruleName = new RuleName();
    @MockBean
    private RuleNameRepository ruleNameRepository;
    @Autowired
    private RuleNameService ruleNameService;

    @BeforeEach
    private void init() {
        ruleName.setId(1);
        ruleName.setName("test");
        ruleName.setSqlPart("SqlPart");
        ruleName.setTemplate("Template");
        ruleName.setJson("Json");
        ruleName.setDescription("Description");
        ruleName.setSqlStr("SqlStr");
    }


    @Test
    final void testFindById() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        RuleName foundRuleName = ruleNameService.findById(1);

        assertThat(foundRuleName).isEqualTo(ruleName);

    }

    @Test
    final void testFindByIdNotFound() throws NotFoundException {
        assertThrows(NotFoundException.class, () -> ruleNameService.findById(0));
    }

    @Test
    final void testDeleteById() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        ruleNameService.delete(1);

        verify(ruleNameRepository, times(1)).delete(any(RuleName.class));
    }

    @Test
    final void testFindAll() {
        List<RuleName> findAll = new ArrayList<>();
        findAll.add(ruleName);

        when(ruleNameRepository.findAll()).thenReturn(findAll);
        List<RuleName> foundRuleName = ruleNameService.findAll();

        assertThat(foundRuleName).isEqualTo(findAll);
    }

    @Test
    final void testCreateRuleName() {
        RuleNameDto ruleNameDto = new RuleNameDto();
        ruleNameDto.setName("test");
        ruleNameDto.setSqlPart("SqlPart");
        ruleNameDto.setTemplate("Template");
        ruleNameDto.setJson("Json");
        ruleNameDto.setDescription("Description");
        ruleNameDto.setSqlStr("SqlStr");

        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(new RuleName());
        RuleName result = ruleNameService.create(ruleNameDto);

        verify(ruleNameRepository, times(1)).save(any(RuleName.class));
        assertNotNull(result);
    }


    @Test
    final void testUpdateRuleName() {
        RuleNameDto ruleNameDto = new RuleNameDto();
        ruleNameDto.setName("testtest");
        ruleNameDto.setSqlPart("SqlPart2");
        ruleNameDto.setTemplate("Template2");
        ruleNameDto.setJson("Json");
        ruleNameDto.setDescription("Description");
        ruleNameDto.setSqlStr("SqlStr");
        RuleName RuleName = new RuleName(ruleNameDto);

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(RuleNameServiceImplTest.ruleName));
        when(ruleNameRepository.getById(1)).thenReturn(RuleNameServiceImplTest.ruleName);
        when(ruleNameRepository.save(RuleName)).thenReturn(RuleName);
        ruleNameService.update(1, ruleNameDto);

        assertThat(RuleNameServiceImplTest.ruleName.getName()).isEqualTo("testtest");
        assertThat(RuleNameServiceImplTest.ruleName.getSqlPart()).isEqualTo("SqlPart2");
        assertThat(RuleNameServiceImplTest.ruleName.getTemplate()).isEqualTo("Template2");
    }

    @Test
    final void testUpdateRuleNameThrowEntityNotFoundException() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());
        RuleNameDto ruleNameDto = new RuleNameDto();
        ruleNameDto.setName("testtest");
        ruleNameDto.setSqlPart("SqlPart2");
        ruleNameDto.setTemplate("Template2");
        ruleNameDto.setJson("Json");
        ruleNameDto.setDescription("Description");
        ruleNameDto.setSqlStr("SqlStr");

        assertThrows(NotFoundException.class, () -> ruleNameService.update(1, ruleNameDto));
    }
}