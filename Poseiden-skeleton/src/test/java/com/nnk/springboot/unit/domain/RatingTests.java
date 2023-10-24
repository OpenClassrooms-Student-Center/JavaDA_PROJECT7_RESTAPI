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
    public void ContextLoads() {}

    @Nested
    public class ValidationTests {

        @Test
        public void ValidationTest() {
            Set<ConstraintViolation<Rating>> result = validator.validate(rating);
            assertTrue(result.isEmpty());
        }

        @Nested
        public class FitchRatingTests
        {
            @Test
            public void ValidationTestIfFitchRatingSize () {
                rating.setFitchRating(longString);
                Set<ConstraintViolation<Rating>> result = validator.validate(rating);
                assertEquals(1, result.size());
                ConstraintViolation<Rating> constraintViolation = (ConstraintViolation<Rating>) result.toArray()[0];
                assertEquals("fitchRating", constraintViolation.getPropertyPath().toString());
                assertEquals(ratingFitchRatingSize, constraintViolation.getMessage());
            }
        }

        @Nested
        public class IdTests
        {
            @Test
            public void ValidationTestIfIdMax () {
                rating.setId(256);
                Set<ConstraintViolation<Rating>> result = validator.validate(rating);
                assertEquals(1, result.size());
                ConstraintViolation<Rating> constraintViolation = (ConstraintViolation<Rating>) result.toArray()[0];
                assertEquals("id", constraintViolation.getPropertyPath().toString());
                assertEquals(ratingIdMax, constraintViolation.getMessage());
            }

            @Test
            public void ValidationTestIfIdMin () {
                rating.setId(-1);
                Set<ConstraintViolation<Rating>> result = validator.validate(rating);
                assertEquals(1, result.size());
                ConstraintViolation<Rating> constraintViolation = (ConstraintViolation<Rating>) result.toArray()[0];
                assertEquals("id", constraintViolation.getPropertyPath().toString());
                assertEquals(ratingIdMin, constraintViolation.getMessage());
            }

            @Test
            public void ValidationTestIfIdNotNull () {
                rating.setId(null);
                Set<ConstraintViolation<Rating>> result = validator.validate(rating);
                assertEquals(1, result.size());
                ConstraintViolation<Rating> constraintViolation = (ConstraintViolation<Rating>) result.toArray()[0];
                assertEquals("id", constraintViolation.getPropertyPath().toString());
                assertEquals(ratingIdNotNull, constraintViolation.getMessage());
            }
        }

        @Nested
        public class MoodysRatingTests
        {
            @Test
            public void ValidationTestIfMoodysRatingSize () {
                rating.setMoodysRating(longString);
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
            public void ValidationTestIfOrderMax () {
                rating.setOrder(256);
                Set<ConstraintViolation<Rating>> result = validator.validate(rating);
                assertEquals(1, result.size());
                ConstraintViolation<Rating> constraintViolation = (ConstraintViolation<Rating>) result.toArray()[0];
                assertEquals("order", constraintViolation.getPropertyPath().toString());
                assertEquals(ratingOrderMax, constraintViolation.getMessage());
            }

            @Test
            public void ValidationTestIfOrderMin () {
                rating.setOrder(-1);
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
            public void ValidationTestIfSandPRatingSize () {
                rating.setSandPRating(longString);
                Set<ConstraintViolation<Rating>> result = validator.validate(rating);
                assertEquals(1, result.size());
                ConstraintViolation<Rating> constraintViolation = (ConstraintViolation<Rating>) result.toArray()[0];
                assertEquals("sandPRating", constraintViolation.getPropertyPath().toString());
                assertEquals(ratingSandPRatingSize, constraintViolation.getMessage());
            }
        }
    }
}
