package com.berkozmen.loan_application_system.annotation;

import com.berkozmen.loan_application_system.annotation.validator.IdentityNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IdentityNumberValidator.class)
//@Target represents which elemnet type to be annotated.
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IdentityNumberValidation {
    //error message
    String message() default "Invalid identity number";
    //represents group of constraints
    Class<?>[] groups() default {};
    //represents additional information about annotation
    Class<? extends Payload>[] payload() default {};
}
