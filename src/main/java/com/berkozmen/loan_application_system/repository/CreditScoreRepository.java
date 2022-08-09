package com.berkozmen.loan_application_system.repository;

import com.berkozmen.loan_application_system.model.entity.CreditScore;
import com.berkozmen.loan_application_system.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {


}
