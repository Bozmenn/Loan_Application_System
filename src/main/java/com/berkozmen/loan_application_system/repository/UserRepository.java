package com.berkozmen.loan_application_system.repository;

import com.berkozmen.loan_application_system.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

        // JPQL
        boolean existsByIdentityNumber(String IdentityNumber);

        User findByIdentityNumber(String identityNumber);

        void deleteByIdentityNumber(String identityNumber);

        Optional<User> findById(Long id);

}
