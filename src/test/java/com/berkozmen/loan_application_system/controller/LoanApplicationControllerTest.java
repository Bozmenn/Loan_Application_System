package com.berkozmen.loan_application_system.controller;

import com.berkozmen.loan_application_system.exception.handler.GenericExceptionHandler;
import com.berkozmen.loan_application_system.model.Role;
import com.berkozmen.loan_application_system.model.Status;
import com.berkozmen.loan_application_system.model.entity.LoanApplication;
import com.berkozmen.loan_application_system.model.entity.User;
import com.berkozmen.loan_application_system.repository.LoanApplicationRepository;
import com.berkozmen.loan_application_system.service.LoanApplicationService;
import com.berkozmen.loan_application_system.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoanApplicationControllerTest {

    private MockMvc mvc;

    @Mock
    private LoanApplicationService loanApplicationService;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoanApplicationController loanApplicationController;

    @BeforeEach
    public void setup(){
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(loanApplicationController)
                .setControllerAdvice(new GenericExceptionHandler()).build();
    }

    @Test
    void getAllLoanApplications() throws Exception {
        //init
        List<LoanApplication> expectedList = getSampleLoanApplicationList();
        String expectedListJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedList);
        //when
        Mockito.when(loanApplicationService.getAll()).thenReturn(expectedList);
        //then
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/loanApplications")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();
        //validate
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<LoanApplication> actualList = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<LoanApplication>>() {
        });
        String actualListJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(actualList);
        assertEquals(expectedListJSON,actualListJSON);
    }

    @Test
    void getLoanApplicationByIdentityNumber() throws Exception {
        //init
        LoanApplication expectedLoanApplication = getSampleLoanApplicationList().get(0);
        String expectedLoanApplicationJSON =
                new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedLoanApplication);
        //when
        Mockito.when(userService.getByIdentityNumber(expectedLoanApplication.getUser().getIdentityNumber()))
                .thenReturn(expectedLoanApplication.getUser());
        Mockito.when(loanApplicationService.getByUserId(expectedLoanApplication.getUser().getId()))
                .thenReturn(expectedLoanApplication);
        //then
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/loanApplications/11111111111")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();
        //validate
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        LoanApplication actualLoanApplication =
                new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<LoanApplication>() {});
        String actualLoanApplicationJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(actualLoanApplication);
        assertEquals(expectedLoanApplicationJSON,actualLoanApplicationJSON);
    }

    @Test
    void createLoanApplication() throws Exception {
        //init
        LoanApplication expectedLoanApplication = getSampleLoanApplicationList().get(0);
        String expectedLoanApplicationJSON =
                new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedLoanApplication);
        //when
        Mockito.when(userService.getByIdentityNumber(expectedLoanApplication.getUser().getIdentityNumber()))
                .thenReturn(expectedLoanApplication.getUser());
        Mockito.when(loanApplicationService.create(expectedLoanApplication.getUser()))
                .thenReturn(expectedLoanApplication);
        //then
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/loanApplications/create/11111111111")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();
        //validate
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        LoanApplication actualLoanApplication =
                new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<LoanApplication>() {});
        String actualLoanApplicationJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(actualLoanApplication);
        assertEquals(expectedLoanApplicationJSON,actualLoanApplicationJSON);
    }

    @Test
    void deleteLoanApplication() throws Exception {
        //init
        LoanApplication expectedLoanApplication = getSampleLoanApplicationList().get(0);
        String expectedLoanApplicationJSON =
                new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedLoanApplication);
        //when
        Mockito.when(userService.getByIdentityNumber(expectedLoanApplication.getUser().getIdentityNumber()))
                .thenReturn(expectedLoanApplication.getUser());
        Mockito.doNothing().when(loanApplicationService).delete(expectedLoanApplication.getUser());
        //then
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.delete("/loanApplications/delete/11111111111")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();
        //validate
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertEquals("Related loan application deleted successfully",response.getContentAsString());
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