package com.berkozmen.loan_application_system.annotation.validator;

import com.berkozmen.loan_application_system.annotation.IdentityNumberValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdentityNumberValidator implements ConstraintValidator<IdentityNumberValidation, String> {

/*    --->11 haneli olmalidir
      --->Soldan ilk basamak 0 olamaz.
      --->d10 = ((d1+d3+d5+d7+d9)*7 - (d2+d4+d6+d8))mod10 (Onuncu basamak icin dogrulama islemi)
      --->d11 = sum(d1,d2,d3...d11)mod10 (Onbirinci basamak icin dogrulama islemi)*/

    @Override
    public void initialize(IdentityNumberValidation identityNumber) {
        ConstraintValidator.super.initialize(identityNumber);
    }

    @Override
    public boolean isValid(String identityNumber, ConstraintValidatorContext constraintValidatorContext) {
        if(identityNumber.length()!=11 || identityNumber.charAt(0)=='0'){
            return false;
        }
        int oddSum=0,evenSum=0,controlDigit=0;
        for(int i=0;i<=8;i++){
            if(i%2==0){
                oddSum+=Character.getNumericValue(identityNumber.charAt(i));

            }else{
                evenSum+=Character.getNumericValue(identityNumber.charAt(i));
            }
        }
        controlDigit = (oddSum*7-evenSum)%10;
        if(Character.getNumericValue(identityNumber.charAt(9))!=controlDigit){
            return false;
        }
        if(Character.getNumericValue(identityNumber.charAt(10))!=(controlDigit+evenSum+oddSum)%10){
            return false;
        }
        return true;
    }
}
