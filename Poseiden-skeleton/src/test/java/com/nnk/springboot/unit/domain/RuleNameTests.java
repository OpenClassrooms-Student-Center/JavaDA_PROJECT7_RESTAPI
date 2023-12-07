package com.nnk.springboot.unit.domain;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.domain.RuleName;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = RuleName.class)
public class RuleNameTests extends TestVariables {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @BeforeEach
    public void setUpPerTest() {
        initializeVariables();
    }

    @Test
    public void contextLoads() {}

    @Nested
    public class ValidationTests {

        @Test
        public void validationTest() {
            Set<ConstraintViolation<RuleName>> result = validator.validate(ruleName);
            assertTrue(result.isEmpty());
        }

        @Nested
        public class NameTests
        {
            @Test
            public void validationTestIfNameSize () {
                ruleName.setName(longString126);
                Set<ConstraintViolation<RuleName>> result = validator.validate(ruleName);
                assertEquals(1, result.size());
                ConstraintViolation<RuleName> constraintViolation = (ConstraintViolation<RuleName>) result.toArray()[0];
                assertEquals("name", constraintViolation.getPropertyPath().toString());
                assertEquals(ruleNameNameSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class DescriptionTests
        {
            @Test
            public void validationTestIfDescriptionSize () {
                ruleName.setDescription(longString126);
                Set<ConstraintViolation<RuleName>> result = validator.validate(ruleName);
                assertEquals(1, result.size());
                ConstraintViolation<RuleName> constraintViolation = (ConstraintViolation<RuleName>) result.toArray()[0];
                assertEquals("description", constraintViolation.getPropertyPath().toString());
                assertEquals(ruleNameDescriptionSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class JsonTests
        {
            @Test
            public void validationTestIfJsonSize () {
                ruleName.setJson(longString126);
                Set<ConstraintViolation<RuleName>> result = validator.validate(ruleName);
                assertEquals(1, result.size());
                ConstraintViolation<RuleName> constraintViolation = (ConstraintViolation<RuleName>) result.toArray()[0];
                assertEquals("json", constraintViolation.getPropertyPath().toString());
                assertEquals(ruleNameJsonSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class TemplateTests
        {
            @Test
            public void validationTestIfTemplateSize () {
                ruleName.setTemplate(longString513);
                Set<ConstraintViolation<RuleName>> result = validator.validate(ruleName);
                assertEquals(1, result.size());
                ConstraintViolation<RuleName> constraintViolation = (ConstraintViolation<RuleName>) result.toArray()[0];
                assertEquals("template", constraintViolation.getPropertyPath().toString());
                assertEquals(ruleNameTemplateSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class SqlStrTests
        {
            @Test
            public void validationTestIfSqlStrSize () {
                ruleName.setSqlStr(longString126);
                Set<ConstraintViolation<RuleName>> result = validator.validate(ruleName);
                assertEquals(1, result.size());
                ConstraintViolation<RuleName> constraintViolation = (ConstraintViolation<RuleName>) result.toArray()[0];
                assertEquals("sqlStr", constraintViolation.getPropertyPath().toString());
                assertEquals(ruleNameSqlStrSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class SqlPartTests
        {
            @Test
            public void validationTestIfSqlPartSize () {
                ruleName.setSqlPart(longString126);
                Set<ConstraintViolation<RuleName>> result = validator.validate(ruleName);
                assertEquals(1, result.size());
                ConstraintViolation<RuleName> constraintViolation = (ConstraintViolation<RuleName>) result.toArray()[0];
                assertEquals("sqlPart", constraintViolation.getPropertyPath().toString());
                assertEquals(ruleNameSqlPartSize, constraintViolation.getMessage());
            }
        }
    }
}
