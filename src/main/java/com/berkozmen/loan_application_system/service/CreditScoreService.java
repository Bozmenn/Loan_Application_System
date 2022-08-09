package com.berkozmen.loan_application_system.service;

import com.berkozmen.loan_application_system.model.entity.CreditScore;
import com.berkozmen.loan_application_system.model.entity.User;
import com.berkozmen.loan_application_system.repository.CreditScoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomUtils;

import static org.apache.commons.lang3.RandomUtils.nextLong;
@Slf4j
@Service
public class CreditScoreService {

    @Autowired
    private CreditScoreRepository creditScoreRepository;


    public CreditScore createUserCreditScore(){
        CreditScore creditScore = new CreditScore();
        creditScore.setScore(nextLong(0L,2000L));
        creditScoreRepository.save(creditScore);
        log.info("User credit score created successfully.");
        return creditScore;
    }

}
