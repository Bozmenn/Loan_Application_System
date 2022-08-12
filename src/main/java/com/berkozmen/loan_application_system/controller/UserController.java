package com.berkozmen.loan_application_system.controller;

import com.berkozmen.loan_application_system.model.dto.UserDataDTO;
import com.berkozmen.loan_application_system.model.dto.UserSigninDTO;
import com.berkozmen.loan_application_system.model.dto.UserSignupDTO;
import com.berkozmen.loan_application_system.model.entity.User;
import com.berkozmen.loan_application_system.model.mapper.UserMapper;
import com.berkozmen.loan_application_system.service.CreditScoreService;
import com.berkozmen.loan_application_system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/users")
@Transactional
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CreditScoreService creditScoreService;


    @GetMapping()
    public ResponseEntity getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @GetMapping("/{identityNumber}")
    public ResponseEntity getUserByIdentityNumber(@PathVariable(name = "identityNumber") String identityNumber){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getByIdentityNumber(identityNumber));
    }

    @PostMapping("/signin")
    public ResponseEntity login(@Valid @RequestBody UserSigninDTO userLoginDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.signin(String.valueOf(userLoginDTO.getIdentityNumber()), userLoginDTO.getPassword()));
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody  UserSignupDTO userSignupDTO) {
        User user = UserMapper.UserSignupDTOtoEntity(userSignupDTO);
        user.setCreditScore(creditScoreService.getUserCreditScore());
        return ResponseEntity.status(HttpStatus.OK).body(userService.signup(user,false));
    }

    @DeleteMapping(value = "/delete/{identityNumber}")
    public ResponseEntity delete(@PathVariable String identityNumber) {
        userService.delete(identityNumber);
        return ResponseEntity.status(HttpStatus.OK).body("User " + identityNumber + " deleted successfully.");
    }

    @PutMapping("/update/{identityNumber}")
    public ResponseEntity updateUser(
            @PathVariable String identityNumber,
            @Valid @RequestBody UserDataDTO userDataDTO)
    {
        userService.update(identityNumber,userDataDTO);
        return ResponseEntity.status(HttpStatus.OK).body("User " + identityNumber + " updated successfully.");
    }

}
