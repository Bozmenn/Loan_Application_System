package com.berkozmen.loan_application_system.controller;

import com.berkozmen.loan_application_system.exception.handler.GenericExceptionHandler;
import com.berkozmen.loan_application_system.model.Role;
import com.berkozmen.loan_application_system.model.dto.UserDataDTO;
import com.berkozmen.loan_application_system.model.dto.UserSigninDTO;
import com.berkozmen.loan_application_system.model.dto.UserSignupDTO;
import com.berkozmen.loan_application_system.model.entity.User;
import com.berkozmen.loan_application_system.service.CreditScoreService;
import com.berkozmen.loan_application_system.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mvc;

    @Mock
    private UserService userService;

    @Mock
    private CreditScoreService creditScoreService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup(){
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GenericExceptionHandler()).build();
    }

    @Test
    void getAllUsers() throws Exception{
        //init
        List<User> expectedList = getSampleUserList();
        String expectedListJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedList);
        //when
        Mockito.when(userService.getAll()).thenReturn(expectedList);
        //then
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();
        //validate
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<User> actualUserList = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<User>>() {
        });
        String actualUserListJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(actualUserList);
        assertEquals(expectedListJSON,actualUserListJSON);
    }

    @Test
    void getUserByIdentityNumber() throws Exception{
        //init
        User expectedUser = getSampleUserList().get(0);
        String expectedUserJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedUser);
        //when
        Mockito.when(userService.getByIdentityNumber(expectedUser.getIdentityNumber())).thenReturn(expectedUser);
        //then
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/users/86451948016")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();
        //validate
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        User actualUser =
                new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<User>() {});
        String actualUserJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(actualUser);
        assertEquals(expectedUserJSON,actualUserJSON);

    }

    @Test
    void login() throws Exception {
        //init
        UserSigninDTO userSigninDTO = new UserSigninDTO("86451948016", "pass123");
        String userSigninDTOJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(userSigninDTO);
        //when
        Mockito.when(userService.signin(userSigninDTO.getIdentityNumber(),userSigninDTO.getPassword())).thenReturn("Token");
        //then
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/users/signin")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userSigninDTOJSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();
        //validate
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertEquals("Token",response.getContentAsString());
    }



    @Test
    void signup() throws Exception {
        //init
        UserSignupDTO userSignupDTO = new UserSignupDTO("86451948016","Name1","Surname1","pass123",10000L,"5553332211");
        User user = new User(null,"86451948016","Name1","Surname1","pass123",10000L,"5553332211",750L,new ArrayList<>());
        String userSignupDTOJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(userSignupDTO);
        //when
        Mockito.when(creditScoreService.getUserCreditScore()).thenReturn(user.getCreditScore());
        Mockito.when(userService.signup(Mockito.any(),Mockito.anyBoolean())).thenReturn("Token");
        //then
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.post("/users/signup")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(userSignupDTOJSON)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();
        //validate
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertEquals("Token",response.getContentAsString());
    }

    @Test
    void delete() throws Exception {
        //init
        String identityNumber = "86451948016";
        //when
        Mockito.doNothing().when(userService).delete(identityNumber);
        //then
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.delete("/users/delete/86451948016")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();
        //validate
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Mockito.verify(userService,Mockito.times(1)).delete(identityNumber);
        assertEquals("User " + identityNumber + " deleted successfully.",response.getContentAsString());
    }

    @Test
    void updateUser() throws Exception {
        //init
        String identityNumber = "86451948016";
        UserDataDTO userDataDTO = new UserDataDTO("Name1","Surname1","pass123",10000L,"5553332211");
        String userDataDTOJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(userDataDTO);
        //when
        Mockito.doNothing().when(userService).update(identityNumber,userDataDTO);
        //then
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.put("/users/update/86451948016")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDataDTOJSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();
        //validate
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Mockito.verify(userService,Mockito.times(1)).update(identityNumber,userDataDTO);
        assertEquals("User " + identityNumber + " updated successfully.",response.getContentAsString());
    }

    private List<User> getSampleUserList(){
        List<Role> userRole = new ArrayList<>();
        userRole.add(Role.ROLE_ADMIN);
        List<User> userList = new ArrayList<>();
        User user1 = new User(1L, "86451948016", "Name1", "Surname1", "pass123", 10000L, "5551112233", 500L,userRole );
        User user2 = new User(2L, "22222222222", "Name2", "Surname2", "pass1234", 20000L, "5551112233",1000L, userRole);
        User user3 = new User(3L, "33333333333", "Name3", "Surname3", "pass12345", 30000L, "5551112233", 1500L, userRole);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        return userList;
    }
}
