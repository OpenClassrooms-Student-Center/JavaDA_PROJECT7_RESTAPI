package com.nnk.springboot.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nnk.springboot.domain.BidList;

@Component
public class BidValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
	return BidList.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "account", "NotEmpty.account");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "NotEmpty.type");
    }

}