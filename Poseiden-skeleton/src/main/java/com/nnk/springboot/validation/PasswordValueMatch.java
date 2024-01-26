package com.nnk.springboot.validation;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordFieldsValueMatchValidator.class)
@Documented
public @interface PasswordValueMatch {
    String message() default "Fields values don't match!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String field();

    @Target({ TYPE })
    @Retention(RUNTIME)
    @interface List {
        PasswordValueMatch[] value();
    }
}
