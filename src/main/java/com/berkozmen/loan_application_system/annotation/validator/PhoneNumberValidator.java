package com.berkozmen.loan_application_system.annotation.validator;

import com.berkozmen.loan_application_system.annotation.IdentityNumberValidation;
import com.berkozmen.loan_application_system.annotation.PhoneNumberValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValidation, String> {
    @Override
    public void initialize(PhoneNumberValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        // pattern excepts:
        // 5551112233
        // 555111-2233
        // 555-111-2233
        String pattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        if (phoneNumber.matches(pattern)) {
            return true;
        } else {
            return false;
        }
    }
}
