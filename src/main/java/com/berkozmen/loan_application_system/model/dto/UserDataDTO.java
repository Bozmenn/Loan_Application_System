package com.berkozmen.loan_application_system.model.dto;

import com.berkozmen.loan_application_system.annotation.PhoneNumberValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDTO {

    private String name;

    private String surname;

    @Size(min = 5, message = "Minimum password length: 5 characters")
    private String password;

    private Long monthlySalary;

    @PhoneNumberValidation
    private String phone;
}
