package com.berkozmen.loan_application_system.model.dto;

import com.berkozmen.loan_application_system.annotation.IdentityNumberValidation;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserSigninDTO {

    @IdentityNumberValidation
    private String identityNumber;
    @NotBlank
    private String password;

}
