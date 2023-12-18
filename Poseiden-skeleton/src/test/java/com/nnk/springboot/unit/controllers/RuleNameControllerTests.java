package com.nnk.springboot.unit.controllers;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RuleNameController.class)
public class RuleNameControllerTests extends TestVariables {

    @Autowired
    RuleNameController ruleNameController;

    @MockBean
    private RuleNameService ruleNameService;

    @MockBean
    private BindingResult bindingResult;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        ruleName.setId(1);

        when(ruleNameService.findAll()).thenReturn(ruleNameList);
        when(ruleNameService.findById(any(Integer.class))).thenReturn(ruleNameOptional);
        when(ruleNameService.findById(any(Integer.class))).thenReturn(ruleNameOptional);
        when(bindingResult.hasErrors()).thenReturn(false);
    }

    @Test
    public void ContextLoads() {}

    @Nested
    public class HomeTests
    {
        @Test
        public void homeTest () {
            assertEquals("ruleName/list", ruleNameController.home(model));
            verify(ruleNameService, Mockito.times(1)).findAll();
        }
    }

    @Nested
    public class AddRuleNameFormTests {
        @Test
        public void addRuleNameFormTest () {
            assertEquals("ruleName/add", ruleNameController.addRuleNameForm(ruleName));
        }
        @Test
        public void addRuleNameFormTestIfEmpty () {
            assertEquals("ruleName/add", ruleNameController.addRuleNameForm(new RuleName()));
        }
        @Test
        public void addRuleNameFormTestIfNull () {
            assertEquals("ruleName/add", ruleNameController.addRuleNameForm(null));
        }
    }

    @Nested
    public class ValidateTests {
        @Test
        public void validateTest () {
            assertEquals("redirect:/ruleName/list", ruleNameController.validate(ruleName, bindingResult, model));
            verify(ruleNameService, Mockito.times(1)).save(any(RuleName.class));
        }
        @Test
        public void validateTestIfInvalidRuleName () {
            when(bindingResult.hasErrors()).thenReturn(true);
            assertEquals("ruleName/add", ruleNameController.validate(ruleName, bindingResult, model));
            verify(ruleNameService, Mockito.times(0)).save(any(RuleName.class));
        }
    }

    @Nested
    public class ShowUpdateFormTests {
        @Test
        public void showUpdateFormTest () {
            assertEquals("ruleName/update", ruleNameController.showUpdateForm(ruleName.getId(), model));
            verify(ruleNameService, Mockito.times(1)).findById(any(Integer.class));
        }
        @Test
        public void showUpdateFormTestIfRuleNameNotFound () {
            when(ruleNameService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertThrows(IllegalArgumentException.class, () -> ruleNameController.showUpdateForm(ruleName.getId(), model));
            verify(ruleNameService, Mockito.times(1)).findById(any(Integer.class));
        }
    }

    @Nested
    public class UpdateRuleNameTests {
        @Test
        public void updateRuleNameTest () {
            assertEquals("redirect:/ruleName/list", ruleNameController.updateRuleName(ruleName.getId(), ruleName, bindingResult, model));
            verify(ruleNameService, Mockito.times(1)).findById(any(Integer.class));
            verify(ruleNameService, Mockito.times(1)).save(any(RuleName.class));
        }
        @Test
        public void updateRuleNameTestIfInvalidRuleName () {
            when(bindingResult.hasErrors()).thenReturn(true);
            assertEquals("redirect:/ruleName/update/{id}", ruleNameController.updateRuleName(ruleName.getId(), ruleName, bindingResult, model));
            verify(ruleNameService, Mockito.times(0)).findById(any(Integer.class));
            verify(ruleNameService, Mockito.times(0)).save(any(RuleName.class));
        }
        @Test
        public void updateRuleNameTestIfRuleNameNotInDB () {
            when(ruleNameService.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertThrows(IllegalArgumentException.class, () -> ruleNameController.updateRuleName(ruleName.getId(), ruleName, bindingResult, model));
            verify(ruleNameService, Mockito.times(1)).findById(any(Integer.class));
            verify(ruleNameService, Mockito.times(0)).save(any(RuleName.class));
        }
    }

    @Nested
    public class DeleteRuleNameTests {
        @Test
        public void deleteRuleNameTest () {
            assertEquals("redirect:/ruleName/list", ruleNameController.deleteRuleName(ruleName.getId(), model));
            verify(ruleNameService, Mockito.times(1)).deleteById(any(Integer.class));
        }
    }
}
