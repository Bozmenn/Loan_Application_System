package com.berkozmen.loan_application_system.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@Data
public class UserDataDTO {

    private String name;

    private String surname;

    private String password;

    private Long monthlySalary;

    private String phone;
}
