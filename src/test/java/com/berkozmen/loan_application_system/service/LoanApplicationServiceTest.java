package com.berkozmen.loan_application_system.service;

import com.berkozmen.loan_application_system.exception.EntityNotFoundException;
import com.berkozmen.loan_application_system.model.Role;
import com.berkozmen.loan_application_system.model.Status;
import com.berkozmen.loan_application_system.model.entity.LoanApplication;
import com.berkozmen.loan_application_system.model.entity.User;
import com.berkozmen.loan_application_system.repository.LoanApplicationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceTest {

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @InjectMocks
    private LoanApplicationService loanApplicationService;

    @Test
    void getAll() throws JsonProcessingException {
        //init
        List<LoanApplication> expectedLoanApplicationList = getSampleLoanApplicationList();
        String expectedLoanApplicationListJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedLoanApplicationList);
        //when
        Mockito.when(loanApplicationRepository.findAll()).thenReturn(expectedLoanApplicationList);
        //then
        List<LoanApplication> actualLoanApplicationList = loanApplicationService.getAll();
        String actualLoanApplicationListJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(actualLoanApplicationList);
        //validate
        Assertions.assertEquals(actualLoanApplicationListJSON,expectedLoanApplicationListJSON);
    }

    @Test
    void getByUserId_successful() throws JsonProcessingException {
        //init
        LoanApplication expectedLoanApplication = getSampleLoanApplicationList().get(0);
        Optional<LoanApplication> optExpectedLoanApplication = Optional.of(expectedLoanApplication);
        String expectedLoanApplicationJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedLoanApplication);
        //when
        Mockito.when(loanApplicationRepository.findByUserId(Mockito.any())).thenReturn(optExpectedLoanApplication);
        //then
        LoanApplication actualLoanApplication = loanApplicationService.getByUserId(Mockito.any());
        String actualLoanApplicationJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(actualLoanApplication);
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
    void create_forCreditScoreLower500() throws JsonProcessingException {
        //init
        User user = new User(1L, "11111111111", "Name1", "Surname1", "pass123", 10000L, "5551112233", 350L,new ArrayList<>());
        LoanApplication expectedLoanApplication = new LoanApplication(1L, user, Status.DENIED, 0L,new Date());
        String expectedLoanApplicationJSON
                = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedLoanApplication);
        //when
        Mockito.when(loanApplicationRepository.save(Mockito.any())).thenReturn(expectedLoanApplication);
        //then
        LoanApplication actualLoanApplication = loanApplicationService.create(user);
        String actualLoanApplicationJSON
                = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(actualLoanApplication);
        //validate
        Assertions.assertEquals(expectedLoanApplicationJSON,actualLoanApplicationJSON);
        Mockito.verify(loanApplicationRepository,Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void create_forCreditScoreUpper500Lower1000_MonthlySalaryLower5000() throws JsonProcessingException {
        //init
        User user = new User(1L, "11111111111", "Name1", "Surname1", "pass123", 3500L, "5551112233", 750L,new ArrayList<>());
        LoanApplication expectedLoanApplication = new LoanApplication(1L, user, Status.APPROVED, 10000L,new Date());
        String expectedLoanApplicationJSON
                = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedLoanApplication);
        //when
        Mockito.when(loanApplicationRepository.save(Mockito.any())).thenReturn(expectedLoanApplication);
        //then
        LoanApplication actualLoanApplication = loanApplicationService.create(user);
        String actualLoanApplicationJSON
                = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(actualLoanApplication);
        //validate
        Assertions.assertEquals(expectedLoanApplicationJSON,actualLoanApplicationJSON);
        Mockito.verify(loanApplicationRepository,Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void create_forCreditScoreUpper500Lower1000_MonthlySalaryUpper5000() throws JsonProcessingException {
        //init
        User user = new User(1L, "11111111111", "Name1", "Surname1", "pass123", 7500L, "5551112233", 750L,new ArrayList<>());
        LoanApplication expectedLoanApplication = new LoanApplication(1L, user, Status.APPROVED, 20000L,new Date());
        String expectedLoanApplicationJSON
                = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedLoanApplication);
        //when
        Mockito.when(loanApplicationRepository.save(Mockito.any())).thenReturn(expectedLoanApplication);
        //then
        LoanApplication actualLoanApplication = loanApplicationService.create(user);
        String actualLoanApplicationJSON
                = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(actualLoanApplication);
        //validate
        Assertions.assertEquals(expectedLoanApplicationJSON,actualLoanApplicationJSON);
        Mockito.verify(loanApplicationRepository,Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void create_forCreditScoreUpper1000() throws JsonProcessingException {
        //init
        User user = new User(1L, "11111111111", "Name1", "Surname1", "pass123", 10000L, "5551112233", 1500L,new ArrayList<>());
        LoanApplication expectedLoanApplication = new LoanApplication(1L, user, Status.APPROVED, 40000L,new Date());
        String expectedLoanApplicationJSON
                = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedLoanApplication);
        //when
        Mockito.when(loanApplicationRepository.save(Mockito.any())).thenReturn(expectedLoanApplication);
        //then
        LoanApplication actualLoanApplication = loanApplicationService.create(user);
        String actualLoanApplicationJSON
                = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(actualLoanApplication);
        //validate
        Assertions.assertEquals(expectedLoanApplicationJSON,actualLoanApplicationJSON);
        Mockito.verify(loanApplicationRepository,Mockito.times(1)).save(Mockito.any());
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
                        1000L, new ArrayList<Role>()),
                Status.APPROVED,
                80000L,new Date());
        LoanApplication loanApplication2 = new LoanApplication(2L, new User(), Status.DENIED, 30000L,new Date());
        LoanApplication loanApplication3 = new LoanApplication(3L, new User(), Status.APPROVED, 40000L,new Date());
        loanApplicationList.add(loanApplication1);
        loanApplicationList.add(loanApplication2);
        loanApplicationList.add(loanApplication3);
        return loanApplicationList;
    }
}
