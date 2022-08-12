package com.berkozmen.loan_application_system.service;

import com.berkozmen.loan_application_system.exception.CustomJwtException;
import com.berkozmen.loan_application_system.model.Role;
import com.berkozmen.loan_application_system.model.dto.UserDataDTO;
import com.berkozmen.loan_application_system.model.entity.User;
import com.berkozmen.loan_application_system.model.mapper.UserMapper;
import com.berkozmen.loan_application_system.repository.UserRepository;
import com.berkozmen.loan_application_system.security.JwtTokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;


    @InjectMocks
    private UserService userService;

    @Test
    void getAll() throws JsonProcessingException {
        //init
        List<User> expectedUserList = getSampleUserList();
        String expectedUserListJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedUserList);
        //when
        Mockito.when(userRepository.findAll()).thenReturn(expectedUserList);
        //then
        List<User> actualUserList = userService.getAll();
        String actualUserListJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(actualUserList);
        //validate
        Assertions.assertEquals(actualUserListJSON,expectedUserListJSON);
        Mockito.verify(userRepository,Mockito.times(1)).findAll();
    }

    @Test
    void getByIdentityNumber_successful() throws JsonProcessingException {
        //init
        User expectedUser = getSampleUserList().get(0);
        String expectedUserJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(expectedUser);
        //when
        Mockito.when(userRepository.findByIdentityNumber(expectedUser.getIdentityNumber())).thenReturn(expectedUser);
        //then
        User actualUser = userService.getByIdentityNumber(expectedUser.getIdentityNumber());
        String actualUserJSON = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(actualUser);
        //validate
        Assertions.assertEquals(actualUserJSON,expectedUserJSON);
        Mockito.verify(userRepository,Mockito.times(1)).findByIdentityNumber(expectedUser.getIdentityNumber());

    }

    @Test
    void getByIdentityNumber_ThrowsCustomJWTException() {
        //init
        //when
        Mockito.when(userRepository.findByIdentityNumber(Mockito.any())).thenThrow(CustomJwtException.class);
        //then
        //validate
        Assert.assertThrows(CustomJwtException.class,()->
            userService.getByIdentityNumber(Mockito.any()));

    }

    @Test
    void signin_successful() {
        //init
        User user = getSampleUserList().get(0);
        Authentication expectedAuthenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getIdentityNumber(), user.getPassword()));
        String expectedToken = jwtTokenProvider.createToken(user.getIdentityNumber(), user.getRoles());
        //when
        Mockito.when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getIdentityNumber(), user.getPassword())))
                .thenReturn(expectedAuthenticate);
        Mockito.when(userRepository.findByIdentityNumber(user.getIdentityNumber())).thenReturn(user);
        Mockito.when(jwtTokenProvider.createToken(
                user.getIdentityNumber(), user.getRoles())).thenReturn(expectedToken);
        //then
        String actualToken = userService.signin(user.getIdentityNumber(), user.getPassword());
        //validate
        Assertions.assertEquals(actualToken,expectedToken);
    }

    @Test
    void signin_ThrowsCustomJWTException() {
        //init
        User user = getSampleUserList().get(0);
        //when
        Mockito.when(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getIdentityNumber(), user.getPassword()))).thenThrow(CustomJwtException.class);
        //then
        //validate
        Assertions.assertThrows(CustomJwtException.class,
                ()->userService.signin(user.getIdentityNumber(),user.getPassword()));
    }

    @Test
    void signup_successful() {
        //init
        User user = getSampleUserList().get(0);
        String expectedToken = jwtTokenProvider.createToken(user.getIdentityNumber(), user.getRoles());
        //when
        Mockito.when(userRepository.existsByIdentityNumber(user.getIdentityNumber())).thenReturn(false);
        Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(jwtTokenProvider.createToken(
                user.getIdentityNumber(), user.getRoles())).thenReturn(expectedToken);
        //then
        String actualToken = userService.signup(user, true);
        //validate
        Assertions.assertEquals(actualToken,expectedToken);
    }

    @Test
    void signup_ThrowsCustomJWTException() {
        //init
        User user = getSampleUserList().get(0);
        //when
        Mockito.when(userRepository.existsByIdentityNumber(user.getIdentityNumber())).thenReturn(true);
        //then
        //validate
        Assertions.assertThrows(CustomJwtException.class,()->userService.signup(user,true));
    }

    @Test
    void delete() {
        //init
        User user = getSampleUserList().get(0);
        //when
        Mockito.when(userRepository.existsByIdentityNumber(user.getIdentityNumber())).thenReturn(true);
        Mockito.doNothing().when(userRepository).deleteByIdentityNumber(user.getIdentityNumber());
        //then
        userService.delete(user.getIdentityNumber());
        //validate
        Mockito.verify(userRepository,Mockito.times(1))
                .deleteByIdentityNumber(user.getIdentityNumber());
    }

    @Test
    void update() {
        //init
        User user = getSampleUserList().get(0);
        UserDataDTO userDataDTO = new UserDataDTO("Name1", "Surname1", "pass123", 10000L, "5551112233");
        //when
        Mockito.when(userRepository.findByIdentityNumber(user.getIdentityNumber())).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        //then
        userService.update(user.getIdentityNumber(),userDataDTO);
        //validate
        Mockito.verify(userRepository,Mockito.times(1)).save(user);
    }

    private List<User> getSampleUserList(){
        List<Role> userRole = new ArrayList<>();
        userRole.add(Role.ROLE_ADMIN);
        List<User> userList = new ArrayList<>();
        User user1 = new User(1L, "11111111111", "Name1", "Surname1", "pass123", 10000L, "5551112233", 500L,userRole );
        User user2 = new User(2L, "22222222222", "Name2", "Surname2", "pass1234", 20000L, "5551112233", 1000L, userRole);
        User user3 = new User(3L, "33333333333", "Name3", "Surname3", "pass12345", 30000L, "5551112233", 1500L, userRole);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        return userList;
    }
}
