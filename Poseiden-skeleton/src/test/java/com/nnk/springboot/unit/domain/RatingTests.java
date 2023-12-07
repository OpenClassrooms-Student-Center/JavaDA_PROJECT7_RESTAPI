package com.nnk.springboot.unit.domain;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.domain.Rating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Rating.class)
public class RatingTests extends TestVariables {

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
            Set<ConstraintViolation<Rating>> result = validator.validate(rating);
            assertTrue(result.isEmpty());
        }

        @Nested
        public class FitchRatingTests
        {
            @Test
            public void validationTestIfFitchRatingSize () {
                rating.setFitchRating(longString126);
                Set<ConstraintViolation<Rating>> result = validator.validate(rating);
                assertEquals(1, result.size());
                ConstraintViolation<Rating> constraintViolation = (ConstraintViolation<Rating>) result.toArray()[0];
                assertEquals("fitchRating", constraintViolation.getPropertyPath().toString());
                assertEquals(ratingFitchRatingSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class MoodysRatingTests
        {
            @Test
            public void validationTestIfMoodysRatingSize () {
                rating.setMoodysRating(longString126);
                Set<ConstraintViolation<Rating>> result = validator.validate(rating);
                assertEquals(1, result.size());
                ConstraintViolation<Rating> constraintViolation = (ConstraintViolation<Rating>) result.toArray()[0];
                assertEquals("moodysRating", constraintViolation.getPropertyPath().toString());
                assertEquals(ratingMoodysRatingSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class OrderTests
        {
            @Test
            public void validationTestIfOrderMax () {
                rating.setOrder(128);
                Set<ConstraintViolation<Rating>> result = validator.validate(rating);
                assertEquals(1, result.size());
                ConstraintViolation<Rating> constraintViolation = (ConstraintViolation<Rating>) result.toArray()[0];
                assertEquals("order", constraintViolation.getPropertyPath().toString());
                assertEquals(ratingOrderMax, constraintViolation.getMessage());
            }

            @Test
            public void validationTestIfOrderMin () {
                rating.setOrder(-129);
                Set<ConstraintViolation<Rating>> result = validator.validate(rating);
                assertEquals(1, result.size());
                ConstraintViolation<Rating> constraintViolation = (ConstraintViolation<Rating>) result.toArray()[0];
                assertEquals("order", constraintViolation.getPropertyPath().toString());
                assertEquals(ratingOrderMin, constraintViolation.getMessage());
            }
        }

        @Nested
        public class SandPRatingTests
        {
            @Test
            public void validationTestIfSandPRatingSize () {
                rating.setSandPRating(longString126);
                Set<ConstraintViolation<Rating>> result = validator.validate(rating);
                assertEquals(1, result.size());
                ConstraintViolation<Rating> constraintViolation = (ConstraintViolation<Rating>) result.toArray()[0];
                assertEquals("sandPRating", constraintViolation.getPropertyPath().toString());
                assertEquals(ratingSandPRatingSize, constraintViolation.getMessage());
            }
        }
    }
}
