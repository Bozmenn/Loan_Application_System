package com.berkozmen.loan_application_system.model.dto;

import com.berkozmen.loan_application_system.annotation.IdentityNumberValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSigninDTO {

    @IdentityNumberValidation
    private String identityNumber;
    @NotBlank
    private String password;

}
