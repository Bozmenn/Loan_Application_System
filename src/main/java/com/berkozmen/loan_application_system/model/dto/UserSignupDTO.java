package com.berkozmen.loan_application_system.model.dto;

import com.berkozmen.loan_application_system.annotation.IdentityNumberValidation;
import com.berkozmen.loan_application_system.annotation.PhoneNumberValidation;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserSignupDTO {

    @IdentityNumberValidation
    private String identityNumber;

    @NotBlank(message = "Name cannot be empty.")
    //@ApiModelProperty(value = "User name field of User object")
    private String name;

    @NotBlank(message = "Surname cannot be empty.")
    //@ApiModelProperty(value = "User name field of User object")
    private String surname;

    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 5, message = "Minimum password length: 5 characters")
    //@ApiModelProperty(value = "Password field of User object")
    private String password;

    @NotNull(message = "Salary information cannot be empty.")
    //@ApiModelProperty(value = "Email field of User object")
    private Long monthlySalary;

    @PhoneNumberValidation
    private String phone;

}
