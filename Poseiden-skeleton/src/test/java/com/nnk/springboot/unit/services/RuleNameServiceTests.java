package com.nnk.springboot.unit.services;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RuleNameService.class)
public class RuleNameServiceTests extends TestVariables {

    @Autowired
    RuleNameService ruleNameService;

    @MockBean
    private RuleNameRepository ruleNameRepository;

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
        ruleName.setId(1);
        when(ruleNameRepository.findAll()).thenReturn(ruleNameList);
        when(ruleNameRepository.findById(any(Integer.class))).thenReturn(ruleNameOptional);
    }

    @Test
    public void contextLoads() {
    }

    @Nested
    public class FindAllTests {
        @Test
        public void findAllTest() {
            assertEquals(ruleNameList, ruleNameService.findAll());
            verify(ruleNameRepository, Mockito.times(1)).findAll();
        }
    }

    @Nested
    public class FindByIdTests {
        @Test
        public void findByIdTest() {
            assertEquals(ruleNameOptional, ruleNameService.findById(ruleName.getId()));
            verify(ruleNameRepository, Mockito.times(1)).findById(any(Integer.class));
        }

        @Test
        public void findByIdTestIfNotFound() {
            when(ruleNameRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
            assertEquals(Optional.empty(), ruleNameService.findById(ruleName.getId()));
            verify(ruleNameRepository, Mockito.times(1)).findById(any(Integer.class));
        }

        @Test
        public void findByIdTestIfIdNotValid() {
            assertThrows(IllegalArgumentException.class, () -> ruleNameService.findById(null));
            verify(ruleNameRepository, Mockito.times(0)).findById(any(Integer.class));
        }
    }

    @Nested
    public class DeleteByIdTests {
        @Test
        public void deleteByIdTest() {
            ruleNameService.deleteById(ruleName.getId());
            verify(ruleNameRepository, Mockito.times(1)).deleteById(any(Integer.class));
        }

        @Test
        public void deleteByIdTestIfIdNotValid() {
            assertThrows(IllegalArgumentException.class, () -> ruleNameService.deleteById(null));
            verify(ruleNameRepository, Mockito.times(0)).deleteById(any(Integer.class));
        }
    }
}
