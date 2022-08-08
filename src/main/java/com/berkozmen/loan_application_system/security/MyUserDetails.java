package com.berkozmen.loan_application_system.security;


import com.berkozmen.loan_application_system.model.entity.User;
import com.berkozmen.loan_application_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identityNumber) throws UsernameNotFoundException {
        User user = userRepository.findByIdentityNumber(identityNumber);

        if (user == null) {
            throw new UsernameNotFoundException("User '" + identityNumber + "' not found");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(identityNumber)
                .password(user.getPassword())
                .authorities(user.getRoles())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();

    }

}
