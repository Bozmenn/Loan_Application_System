package com.berkozmen.loan_application_system.annotation;

import com.berkozmen.loan_application_system.annotation.validator.IdentityNumberValidator;
import com.berkozmen.loan_application_system.annotation.validator.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
//@Target represents which elemnet type to be annotated.
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberValidation {
    //error message
    String message() default "Invalid phone number";
    //represents group of constraints
    Class<?>[] groups() default {};
    //represents additional information about annotation
    Class<? extends Payload>[] payload() default {};
}
