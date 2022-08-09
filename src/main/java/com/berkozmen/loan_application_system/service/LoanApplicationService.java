package com.berkozmen.loan_application_system.service;

import com.berkozmen.loan_application_system.exception.EntityNotFoundException;
import com.berkozmen.loan_application_system.model.Status;
import com.berkozmen.loan_application_system.model.entity.LoanApplication;
import com.berkozmen.loan_application_system.model.entity.User;
import com.berkozmen.loan_application_system.repository.LoanApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;


    //Credit limit multiplier (default 4)
    private Long CLM = 4L;

    public List<LoanApplication> getAll() {
        return loanApplicationRepository.findAll();
    }

    public LoanApplication getByUser(User user) {
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findByUser(user);
        return loanApplication.orElseThrow(() -> {
            log.error("Related loan application cannot find by user");
            return new EntityNotFoundException("Loan Application");
        });
    }

    public LoanApplication create(User user){
        LoanApplication loanApplication = loanApplicationResultCalculator(user);
        log.info("User loan application created successfully.");
        return loanApplicationRepository.save(loanApplication);
    }

    public void delete(User user ){
        loanApplicationRepository.deleteByUser(user);
        log.info("User loan application deleted successfully.");
    }

    private LoanApplication loanApplicationResultCalculator(User user){
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setUser(user);
        if(user.getCreditScore().getScore() < 500L){
            loanApplication.setStatus(Status.DENIED);
            loanApplication.setAllowedCreditLimit(0L);
            return loanApplication;
        } else if (500L <= user.getCreditScore().getScore() & user.getCreditScore().getScore()<1000L) {
            if(user.getMonthlySalary() < 5000L){
                loanApplication.setStatus(Status.APPROVED);
                loanApplication.setAllowedCreditLimit(10000L);
                return loanApplication;
            } else {
                loanApplication.setStatus(Status.APPROVED);
                loanApplication.setAllowedCreditLimit(20000L);
                return loanApplication;
            }
        }else {
            loanApplication.setStatus(Status.APPROVED);
            loanApplication.setAllowedCreditLimit(user.getMonthlySalary() * CLM);
            return loanApplication;
        }
    }

}
