package com.nnk.springboot.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordFieldsValueMatchValidator implements ConstraintValidator<PasswordValueMatch, Object> {

    private String field;
    private String message;

    public void initialize(PasswordValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.message = constraintAnnotation.message();
    }

    public boolean isValid(Object value,
                           ConstraintValidatorContext context) {

        Object fieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(field);

        boolean isValid = false;
        if (fieldValue != null) {
            isValid = true;
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(field)
                    .addConstraintViolation();
        }

        return isValid;

    }

}
