package com.berkozmen.loan_application_system.model.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserSignupDTO {

    @NotBlank
    private String identityNumber;

    @NotBlank
    //@ApiModelProperty(value = "User name field of User object")
    private String name;

    @NotBlank
    //@ApiModelProperty(value = "User name field of User object")
    private String surname;

    @NotBlank
    @Size(min = 5, message = "Minimum password length: 5 characters")
    //@ApiModelProperty(value = "Password field of User object")
    private String password;

    @NotNull
    //@ApiModelProperty(value = "Email field of User object")
    private Long monthlySalary;

    @NotBlank
    private String phone;

}
