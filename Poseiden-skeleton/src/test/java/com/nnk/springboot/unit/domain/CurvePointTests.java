package com.nnk.springboot.unit.domain;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.domain.CurvePoint;
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

@SpringBootTest(classes = CurvePoint.class)
public class CurvePointTests extends TestVariables {

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
            Set<ConstraintViolation<CurvePoint>> result = validator.validate(curvePoint);
            assertTrue(result.isEmpty());
        }
        @Nested
        public class CurveIdTests {
            @Test
            public void validationTestIfCurveIdNegative() {
                curvePoint.setCurveId(-5);
                Set<ConstraintViolation<CurvePoint>> result = validator.validate(curvePoint);
                assertEquals(1, result.size());
                ConstraintViolation<CurvePoint> constraintViolation = (ConstraintViolation<CurvePoint>) result.toArray()[0];
                assertEquals("curveId", constraintViolation.getPropertyPath().toString());
                assertEquals(curvePointCurveIdPositiveOrZero, constraintViolation.getMessage());
            }
        }
    }
}
