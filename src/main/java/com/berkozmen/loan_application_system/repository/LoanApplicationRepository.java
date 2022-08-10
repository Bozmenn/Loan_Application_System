package com.berkozmen.loan_application_system.repository;


import com.berkozmen.loan_application_system.model.entity.LoanApplication;
import com.berkozmen.loan_application_system.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {


    Optional<LoanApplication> findByUserId(Long userId);

    void deleteByUser(User user);

}
