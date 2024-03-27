package com.nnk.springboot.configuration;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomValidator implements ConstraintValidator<CustomValidation, String> {
    @Override
    public void initialize(CustomValidation constraintAnnotation) {
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value.length()<8 || !checkUpperCase(value) || !checkNumber(value) || !checkSymbol(value)){
            return false;
        }
        return true;
    }

    private boolean checkUpperCase(String value){
        for(int i=0; i<value.length();i++){
            if (Character.isUpperCase(value.charAt(i))){
                return true;
            }
        }
        return false;
    }
    private boolean checkNumber(String value){
        for(int i=0; i<value.length();i++){
            if (Character.isDigit(value.charAt(i))){
                return true;
            }
        }
        return false;
    }
    private boolean checkSymbol(String value){
        for(int i=0; i<value.length();i++){
            if (!Character.isLetterOrDigit(value.charAt(i)) && !Character.isWhitespace(value.charAt(i))){
                return true;
            }
        }
        return false;
    }
}
