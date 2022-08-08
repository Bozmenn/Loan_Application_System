package com.berkozmen.loan_application_system.controller;



import com.berkozmen.loan_application_system.model.dto.UserDataDTO;
import com.berkozmen.loan_application_system.model.dto.UserSigninDTO;
import com.berkozmen.loan_application_system.model.dto.UserSignupDTO;
import com.berkozmen.loan_application_system.model.entity.User;
import com.berkozmen.loan_application_system.model.mapper.UserMapper;
import com.berkozmen.loan_application_system.service.UserService;
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
//@Api(value = "User Api documentation")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping()
    //@ApiOperation(value = "User list method")
    public List<User> getAllUsers() {
        return userService.getAll();
    }


    // @ApiOperation(value = "User Sign in  method")
    @PostMapping("/signin")
    public String login(@RequestBody UserSigninDTO userLoginDTO) {
        return userService.signin(String.valueOf(userLoginDTO.getIdentityNumber()), userLoginDTO.getPassword());
    }

    //@ApiOperation(value = "User Sign up  method")
    @PostMapping("/signup")
    public String signup(@RequestBody @Valid UserSignupDTO userSignupDTO) {
        User user = UserMapper.UserSignupDTOtoEntity(userSignupDTO);
        return userService.signup(user,false);
    }

    //@ApiOperation(value = "User delete  method for admin")
    @DeleteMapping(value = "/delete/{identityNumber}")
    public String delete(@PathVariable Long identityNumber) {
        userService.delete(String.valueOf(identityNumber));
        return "User " + String.valueOf(identityNumber) + " has been successfully deleted.";
    }

    //@ApiOperation(value = "User update method")
    @PutMapping("/update/{identityNumber}")
    public ResponseEntity updateUser(
            @PathVariable Long identityNumber,
            @Valid @RequestBody UserDataDTO userDataDTO)
    {
        userService.update(identityNumber,userDataDTO);
        return ResponseEntity.status(HttpStatus.OK).body("User " + String.valueOf(identityNumber) + " successfully updated");
    }

}
