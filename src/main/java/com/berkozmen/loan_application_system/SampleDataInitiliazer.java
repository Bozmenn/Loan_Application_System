package com.berkozmen.loan_application_system;


import com.berkozmen.loan_application_system.model.entity.User;
import com.berkozmen.loan_application_system.repository.UserRepository;
import com.berkozmen.loan_application_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SampleDataInitiliazer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void run(ApplicationArguments args) {

        // Creating a sample Admin USER
        User adminUser = new User("admin-user", "pass1234");
        if (!userRepository.existsByIdentityNumber(adminUser.getIdentityNumber())) {
            userService.signup(adminUser, true);
        }

        // Creating a sample USER
        User sampleUser = new User("sample-user", "pass1234");
        if (!userRepository.existsByIdentityNumber(sampleUser.getIdentityNumber())) {
            userService.signup(sampleUser, false);
        }

    }

}
