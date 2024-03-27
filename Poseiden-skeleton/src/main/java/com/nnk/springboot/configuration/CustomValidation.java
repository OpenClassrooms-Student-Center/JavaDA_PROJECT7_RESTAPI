package com.nnk.springboot.configuration;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomValidator.class)
public @interface CustomValidation {
    String message() default "Invalid Password, you need 8 characters, including at least 1 Uppercase, 1 number and 1 symbol";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}