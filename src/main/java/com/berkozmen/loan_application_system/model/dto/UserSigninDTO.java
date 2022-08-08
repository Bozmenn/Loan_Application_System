package com.berkozmen.loan_application_system.model.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class UserSigninDTO {

    @NotBlank
    private Long identityNumber;
    @NotBlank
    private String password;

}
