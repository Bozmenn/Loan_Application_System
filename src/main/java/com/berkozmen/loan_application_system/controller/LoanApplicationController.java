package com.berkozmen.loan_application_system.controller;

import com.berkozmen.loan_application_system.model.entity.LoanApplication;
import com.berkozmen.loan_application_system.model.entity.User;
import com.berkozmen.loan_application_system.service.LoanApplicationService;
import com.berkozmen.loan_application_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loanApplications")
@Transactional
public class LoanApplicationController {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity getAllLoanApplications() {
        return ResponseEntity.status(HttpStatus.OK).body(loanApplicationService.getAll());
    }

    @GetMapping("/{identityNumber}")
    public ResponseEntity getLoanApplicationByIdentityNumber(@PathVariable(name = "identityNumber") String identityNumber){
        User byIdentityNumber = userService.getByIdentityNumber(identityNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loanApplicationService.getByUserId(byIdentityNumber.getId()));
    }

    @PostMapping("/create/{identityNumber}")
    public ResponseEntity createLoanApplication(@PathVariable(name = "identityNumber") String identityNumber){
        User byIdentityNumber = userService.getByIdentityNumber(identityNumber);
        LoanApplication loanApplication = loanApplicationService.create(byIdentityNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanApplication);
    }

    @DeleteMapping("/delete/{identityNumber}")
    public ResponseEntity deleteLoanApplication(@PathVariable(name = "identityNumber") String identityNumber){
        User byIdentityNumber = userService.getByIdentityNumber(identityNumber);
        loanApplicationService.delete(byIdentityNumber);
        return ResponseEntity.status(HttpStatus.OK).body("Related loan application deleted successfully");
    }

}
