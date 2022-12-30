package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RuleNameServiceImplTest {


    @InjectMocks
    private RuleNameServiceImpl ruleNameService;

    @Mock
    private RuleNameRepository ruleNameRepository;

    private RuleName ruleName1;

    private RuleName ruleName2;


    @Test
    void findAllRuleNameTest() {
        //Given
        List<RuleName> allRuleName = List.of(
                new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart"),
                new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart")
        );
        when(ruleNameRepository.findAll()).thenReturn(allRuleName);

        //When
        List<RuleName> result = ruleNameService.findAll();

        //Then
        Assert.assertEquals(result.get(1), allRuleName.get(1));


    }

    @Test
    void findByIdRuleNameTest() {

        List<RuleName> allRuleName = List.of(
                new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart"),
                new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart")
        );
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(allRuleName.get(1)));

        //When
        Optional<RuleName> result = ruleNameService.findById(1);

        //Then
        Assert.assertEquals(result.get(), allRuleName.get(1));

    }

    @Test
    public void findByIdRuleNameTest_ShouldThrowException() throws DataNotFoundException {
        //Given
        when(ruleNameRepository.findById(3)).thenReturn(Optional.empty());
        // When //Then
        assertThrows(DataNotFoundException.class, () -> ruleNameService.findById(3));
    }

    @Test
    void saveRuleNameTest() {
        //Given
        when(ruleNameRepository.save(ruleName1)).thenReturn(ruleName1);

        //When
        RuleName result = ruleNameService.save(ruleName1);

        //Then
        Assert.assertEquals(result, ruleName1);

    }

    @Test
    void updateRuleNameTest() {
        //Given
        RuleName ruleName = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");
        ruleName.setId(1);
        Optional<RuleName> ruleNameOptional = Optional.of(ruleName);

        when(ruleNameRepository.findById(any())).thenReturn(Optional.of(ruleName));

        when(ruleNameRepository.save(any())).thenReturn(ruleNameOptional.get());

        //When
        RuleName result = ruleNameService.update(ruleNameOptional.get());

        //Then
        Assert.assertEquals(result, ruleName);

    }

//    @Test
//    public void updateRuleNameTest_ShouldThrowException() throws UsernameNotFoundException {
//        //Given
//        RuleName ruleName = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");
//        Optional<RuleName> ruleNameOptional = Optional.of(ruleName);
//
//        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
//
//
//
//        assertThrows(UsernameNotFoundException.class, () -> ruleNameService.update(ruleName));
//    }

    @Test
    void deleteRuleNameTest() {

        //Given
        RuleName ruleName = new RuleName("name", "description", "json", "template", "sqlStr", "sqlPart");

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        //When
        ruleNameService.delete(1);

        //Then
        verify(ruleNameRepository).findById(1);
    }

    @Test
    public void deleteRuleNameTest_ShouldThrowException() throws DataNotFoundException {
        //Given // When //Then
        assertThrows(DataNotFoundException.class, () -> ruleNameService.delete(3));
    }


}