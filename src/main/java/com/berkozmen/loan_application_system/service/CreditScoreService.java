package com.berkozmen.loan_application_system.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.RandomUtils.nextLong;
@Slf4j
@Service
public class CreditScoreService {


    public Long getUserCreditScore(){
        long creditScore = nextLong(0L, 2000L);
        log.info("User credit score successfully taken from credit service");
        return creditScore;
    }

}
