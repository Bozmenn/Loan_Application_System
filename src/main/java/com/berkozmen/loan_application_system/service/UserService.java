package com.berkozmen.loan_application_system.service;

import com.berkozmen.loan_application_system.exception.CustomJwtException;
import com.berkozmen.loan_application_system.model.Role;
import com.berkozmen.loan_application_system.model.dto.UserDataDTO;
import com.berkozmen.loan_application_system.model.entity.User;
import com.berkozmen.loan_application_system.model.mapper.UserMapper;
import com.berkozmen.loan_application_system.repository.UserRepository;
import com.berkozmen.loan_application_system.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;


    public List<User> getAll() {
        return userRepository.findAll();
    }


    public User getByIdentityNumber(String identityNumber) {
        User user = userRepository.findByIdentityNumber(identityNumber);
        if (user == null) {
            log.error("The user doesn't exist");
            throw new CustomJwtException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public String signin(String identityNumber, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(identityNumber, password));
            log.info("User successfully signed in");
            return jwtTokenProvider.createToken(identityNumber, userRepository.findByIdentityNumber(identityNumber).getRoles());
        } catch (AuthenticationException e) {
            log.error("Invalid identityNumber/password supplied");
            throw new CustomJwtException("Invalid identityNumber/password supplied", HttpStatus.BAD_REQUEST);
        }
    }

    public String signup(User user, boolean isAdmin) {
        if (!userRepository.existsByIdentityNumber(user.getIdentityNumber())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role role = isAdmin ? Role.ROLE_ADMIN : Role.ROLE_CLIENT;
            user.setRoles(Collections.singletonList(role));
            userRepository.save(user);
            log.info("User successfully signed up");
            return jwtTokenProvider.createToken(user.getIdentityNumber(), user.getRoles());
        } else {
            throw new CustomJwtException("identityNumber is already in use", HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(String identityNumber) {
        if (userRepository.existsByIdentityNumber(identityNumber)) {
            userRepository.deleteByIdentityNumber(identityNumber);
            log.info("User successfully deleted");
        } else {
            log.error("User not found");
            throw new CustomJwtException("User not found", HttpStatus.NOT_FOUND);
        }
    }

    public void update(String identityNumber, UserDataDTO userDataDTO){
        User updatedUser = getByIdentityNumber(String.valueOf(identityNumber));
        UserMapper.UserDataDTOtoUpdatedUser(updatedUser ,userDataDTO);
        log.info("User successfully updated");
        userRepository.save(updatedUser);
    }

}
