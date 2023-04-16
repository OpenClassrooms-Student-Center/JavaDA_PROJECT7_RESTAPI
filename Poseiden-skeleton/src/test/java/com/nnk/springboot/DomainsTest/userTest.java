package com.nnk.springboot.DomainsTest;

import com.nnk.springboot.domain.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Set;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
public class userTest {

    @Test
    public void validateUserWithErrorsOnUserTest() {
        User user = new User("", "", "", "");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        Assertions.assertEquals(4, violations.size());
    }
    @Test
    public void validateUserWithCorrectUserTest() {
        User user = new User("john", "pass", "johndoe", "ROLE_USER");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        Assertions.assertEquals(0, violations.size());
    }
}
