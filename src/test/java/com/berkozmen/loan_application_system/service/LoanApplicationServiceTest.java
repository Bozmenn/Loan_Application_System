package com.berkozmen.loan_application_system.service;

import com.berkozmen.loan_application_system.exception.EntityNotFoundException;
import com.berkozmen.loan_application_system.model.Role;
import com.berkozmen.loan_application_system.model.Status;
import com.berkozmen.loan_application_system.model.entity.CreditScore;
import com.berkozmen.loan_application_system.model.entity.LoanApplication;
import com.berkozmen.loan_application_system.model.entity.User;
import com.berkozmen.loan_application_system.repository.LoanApplicationRepository;
import com.berkozmen.loan_application_system.utils.ObjectExtensions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceTest {

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @InjectMocks
    private LoanApplicationService loanApplicationService;

    @Test
    void getAll() {
        //init
        List<LoanApplication> expectedLoanApplicationList = getSampleLoanApplicationList();
        String expectedLoanApplicationListJSON = ObjectExtensions.toJson(expectedLoanApplicationList);
        //when
        Mockito.when(loanApplicationRepository.findAll()).thenReturn(expectedLoanApplicationList);
        //then
        List<LoanApplication> actualLoanApplicationList = loanApplicationService.getAll();
        String actualLoanApplicationListJSON = ObjectExtensions.toJson(actualLoanApplicationList);
        //validate
        Assertions.assertEquals(actualLoanApplicationListJSON,expectedLoanApplicationListJSON);
    }

    @Test
    void getByUserId_successful() {
        //init
        LoanApplication expectedLoanApplication = getSampleLoanApplicationList().get(0);
        Optional<LoanApplication> optExpectedLoanApplication = Optional.of(expectedLoanApplication);
        String expectedLoanApplicationJSON = ObjectExtensions.toJson(expectedLoanApplication);
        //when
        Mockito.when(loanApplicationRepository.findByUserId(Mockito.any())).thenReturn(optExpectedLoanApplication);
        //then
        LoanApplication actualLoanApplication = loanApplicationService.getByUserId(Mockito.any());
        String actualLoanApplicationJSON = ObjectExtensions.toJson(actualLoanApplication);
        //validate
        Assertions.assertEquals(actualLoanApplicationJSON,expectedLoanApplicationJSON);
    }

    @Test
    void getByUserId_ThrowsEntityNotFoundException() {
        //init
        //when
        Mockito.when(loanApplicationRepository.findByUserId(Mockito.any())).thenReturn(Optional.empty());
        //then
        //validate
        Assertions.assertThrows(EntityNotFoundException.class,
                ()->loanApplicationService.getByUserId(Mockito.any()));
    }

    @Test
    void create() {
        //init
        LoanApplication expectedLoanApplication = getSampleLoanApplicationList().get(0);
        String expectedLoanApplicationJSON = ObjectExtensions.toJson(expectedLoanApplication);
        User user = expectedLoanApplication.getUser();
        //when
        Mockito.lenient().when(loanApplicationRepository.save(expectedLoanApplication)).thenReturn(expectedLoanApplication);
        //then
        LoanApplication actualLoanApplication = loanApplicationService.create(user);
        String actualLoanApplicationJSON = ObjectExtensions.toJson(actualLoanApplication);
        //validate
        Assertions.assertEquals(expectedLoanApplicationJSON,actualLoanApplicationJSON);
    }

    @Test
    void delete() {
        //init
        User user = getSampleLoanApplicationList().get(0).getUser();
        //when
        Mockito.doNothing().when(loanApplicationRepository).deleteByUser(user);
        //then
        loanApplicationService.delete(user);
        //validate
        Mockito.verify(loanApplicationRepository,Mockito.times(1)).deleteByUser(user);
    }

    private List<LoanApplication> getSampleLoanApplicationList(){
        List<LoanApplication> loanApplicationList = new ArrayList<>();
        LoanApplication loanApplication1 = new LoanApplication(1L,
                new User(1L,"11111111111","user1","surname1",
                        "pass123",20000L,"1111112222",
                        new CreditScore(1L,1000L), new ArrayList<Role>()),
                Status.APPROVED,
                80000L);
        LoanApplication loanApplication2 = new LoanApplication(2L, new User(), Status.DENIED, 30000L);
        LoanApplication loanApplication3 = new LoanApplication(3L, new User(), Status.APPROVED, 40000L);
        loanApplicationList.add(loanApplication1);
        loanApplicationList.add(loanApplication2);
        loanApplicationList.add(loanApplication3);
        return loanApplicationList;
    }
}