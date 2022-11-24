package com.nnk.springboot.validator;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.IUserService;

@Component
public class UserValidator implements Validator {
    @Autowired
    private IUserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
	return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
	User user = (User) target;

	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.userRegistration.email");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userRegistration.password");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.userRegistration.firstName");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.userRegistration.lastName");

//	if (!user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
//	    errors.rejectValue("email", "Incorrect.userRegistration.email");
//	}

	if (user.getPassword().length() < 6 || user.getPassword().length() > 32) {
	    errors.rejectValue("password", "Size.userRegistration.password");
	}

//	Optional<User> userEmailResult = userService.getUserByEmail(user.getEmail());
//	try {
//	    userEmailResult.get();
//	    errors.rejectValue("email", "Duplicate.userRegistration.email");
//	} catch (Exception e) {
//	}

    }

}