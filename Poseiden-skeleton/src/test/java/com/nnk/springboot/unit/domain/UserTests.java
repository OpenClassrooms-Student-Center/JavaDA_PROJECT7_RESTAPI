package com.nnk.springboot.unit.domain;

import com.nnk.springboot.TestVariables;
import com.nnk.springboot.domain.User;
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

@SpringBootTest(classes = User.class)
public class UserTests extends TestVariables {

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
            Set<ConstraintViolation<User>> result = validator.validate(user);
            assertTrue(result.isEmpty());
        }

        @Nested
        public class UsernameTests
        {
            @Test
            public void ValidationTestIfUsernameSize () {
                user.setUsername(longString);
                Set<ConstraintViolation<User>> result = validator.validate(user);
                assertEquals(1, result.size());
                ConstraintViolation<User> constraintViolation = (ConstraintViolation<User>) result.toArray()[0];
                assertEquals("username", constraintViolation.getPropertyPath().toString());
                assertEquals(userUsernameSize, constraintViolation.getMessage());
            }

            @Test
            public void ValidationTestIfUsernameNotBlank () {
                user.setUsername(" ");
                Set<ConstraintViolation<User>> result = validator.validate(user);
                assertEquals(1, result.size());
                ConstraintViolation<User> constraintViolation = (ConstraintViolation<User>) result.toArray()[0];
                assertEquals("username", constraintViolation.getPropertyPath().toString());
                assertEquals(userUsernameNotBlank, constraintViolation.getMessage());
            }
        }

        @Nested
        public class PasswordTests
        {
            @Test
            public void ValidationTestIfPasswordSize () {
                user.setPassword(longString);
                Set<ConstraintViolation<User>> result = validator.validate(user);
                assertEquals(1, result.size());
                ConstraintViolation<User> constraintViolation = (ConstraintViolation<User>) result.toArray()[0];
                assertEquals("password", constraintViolation.getPropertyPath().toString());
                assertEquals(userPasswordSize, constraintViolation.getMessage());
            }

            @Test
            public void ValidationTestIfPasswordNotBlank () {
                user.setPassword(" ");
                Set<ConstraintViolation<User>> result = validator.validate(user);
                assertEquals(1, result.size());
                ConstraintViolation<User> constraintViolation = (ConstraintViolation<User>) result.toArray()[0];
                assertEquals("password", constraintViolation.getPropertyPath().toString());
                assertEquals(userPasswordNotBlank, constraintViolation.getMessage());
            }
        }

        @Nested
        public class FullNameTests
        {
            @Test
            public void ValidationTestIfFullNameSize () {
                user.setFullname(longString);
                Set<ConstraintViolation<User>> result = validator.validate(user);
                assertEquals(1, result.size());
                ConstraintViolation<User> constraintViolation = (ConstraintViolation<User>) result.toArray()[0];
                assertEquals("fullname", constraintViolation.getPropertyPath().toString());
                assertEquals(userFullNameSize, constraintViolation.getMessage());
            }

            @Test
            public void ValidationTestIfFullNameNotBlank () {
                user.setFullname(" ");
                Set<ConstraintViolation<User>> result = validator.validate(user);
                assertEquals(1, result.size());
                ConstraintViolation<User> constraintViolation = (ConstraintViolation<User>) result.toArray()[0];
                assertEquals("fullname", constraintViolation.getPropertyPath().toString());
                assertEquals(userFullNameNotBlank, constraintViolation.getMessage());
            }
        }

        @Nested
        public class RoleTests
        {
            @Test
            public void ValidationTestIfRoleSize () {
                user.setRole(longString);
                Set<ConstraintViolation<User>> result = validator.validate(user);
                assertEquals(1, result.size());
                ConstraintViolation<User> constraintViolation = (ConstraintViolation<User>) result.toArray()[0];
                assertEquals("role", constraintViolation.getPropertyPath().toString());
                assertEquals(userRoleSize, constraintViolation.getMessage());
            }

            @Test
            public void ValidationTestIfRoleNotBlank () {
                user.setRole(" ");
                Set<ConstraintViolation<User>> result = validator.validate(user);
                assertEquals(1, result.size());
                ConstraintViolation<User> constraintViolation = (ConstraintViolation<User>) result.toArray()[0];
                assertEquals("role", constraintViolation.getPropertyPath().toString());
                assertEquals(userRoleNotBlank, constraintViolation.getMessage());
            }
        }
    }

    @Nested
    public class ValidatePasswordTests {

        @Test
        public void ValidatePasswordTest() {
            assertTrue(user.validatePassword());
        }

        @Test
        public void ValidatePasswordTestIfNoUppercase() {
            user.setPassword("passwordtestvalue1!");
            assertFalse(user.validatePassword());
        }

        @Test
        public void ValidatePasswordTestIfNoLowercase() {
            user.setPassword("PASSWORDTESTVALUE1!");
            assertFalse(user.validatePassword());
        }

        @Test
        public void ValidatePasswordTestIfNoDigit() {
            user.setPassword("passwordTestValue!");
            assertFalse(user.validatePassword());
        }

        @Test
        public void ValidatePasswordTestIfNoNonWord() {
            user.setPassword("passwordTestValue1");
            assertFalse(user.validatePassword());
        }

        @Test
        public void ValidatePasswordTestIfTooSmall() {
            user.setPassword("P1!");
            assertFalse(user.validatePassword());
        }
    }
}
