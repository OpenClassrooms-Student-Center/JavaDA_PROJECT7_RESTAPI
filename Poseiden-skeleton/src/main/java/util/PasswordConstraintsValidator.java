package util;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordConstraintsValidator implements ConstraintValidator<Password, String> {


    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {

        PasswordValidator passwordValidator = new PasswordValidator(
                Arrays.asList(
                        //Length rule. Min 10 max 60 characters
                        new LengthRule(10, 60),
                        //At least one upper case letter
                        new UppercaseCharacterRule(1),
                        //At least one number
                        new DigitCharacterRule(1),
                        //At least one special characters
                        new SpecialCharacterRule(1),

                        new WhitespaceRule()));


        RuleResult result = passwordValidator.validate(new PasswordData(password));

        if (result.isValid()) {

            return true;

        }

        //Sending one message each time failed validation.
        constraintValidatorContext
                .buildConstraintViolationWithTemplate(passwordValidator
                        .getMessages(result).stream().findFirst().get())
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;

    }
}
