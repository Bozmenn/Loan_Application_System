package com.berkozmen.loan_application_system.model.mapper;

import com.berkozmen.loan_application_system.model.dto.UserDataDTO;
import com.berkozmen.loan_application_system.model.dto.UserSignupDTO;
import com.berkozmen.loan_application_system.model.entity.User;


public class UserMapper {

    public static User UserSignupDTOtoEntity(UserSignupDTO userSignupDTO){
        User user = new User();
        user.setIdentityNumber(userSignupDTO.getIdentityNumber());
        user.setName(userSignupDTO.getName());
        user.setSurname(userSignupDTO.getSurname());
        user.setPassword(userSignupDTO.getPassword());
        user.setMonthlySalary(userSignupDTO.getMonthlySalary());
        user.setPhone(userSignupDTO.getPhone());
        return user;
    }

    public static User UserDataDTOtoUpdatedUser(User updatedUser, UserDataDTO userDataDTO){
        if(userDataDTO.getName() != null){
            updatedUser.setName(userDataDTO.getName());
        }if(userDataDTO.getSurname() != null){
            updatedUser.setSurname(userDataDTO.getSurname());
        }if(userDataDTO.getPassword() != null){
            updatedUser.setPassword(userDataDTO.getPassword());
        }if(userDataDTO.getMonthlySalary() != null){
            updatedUser.setMonthlySalary(userDataDTO.getMonthlySalary());
        }if(userDataDTO.getPhone() != null){
            updatedUser.setPhone(userDataDTO.getPhone());
        }
        return updatedUser;
    }

}
