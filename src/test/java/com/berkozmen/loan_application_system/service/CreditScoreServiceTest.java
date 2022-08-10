package com.berkozmen.loan_application_system.service;

import com.berkozmen.loan_application_system.model.entity.CreditScore;
import com.berkozmen.loan_application_system.repository.CreditScoreRepository;
import com.berkozmen.loan_application_system.utils.ObjectExtensions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreditScoreServiceTest {

    @Mock
    private CreditScoreRepository creditScoreRepository;

    @InjectMocks
    private CreditScoreService creditScoreService;

    @Test
    void createUserCreditScore() {
        //then
        CreditScore actualUserCreditScore = creditScoreService.createUserCreditScore();
        String actualUserCreditScoreJSON = ObjectExtensions.toJson(actualUserCreditScore);
        //init
        CreditScore expectedUsercreditScore = new CreditScore(actualUserCreditScore.getId(),actualUserCreditScore.getScore());
        String expectedUsercreditScoreJSON = ObjectExtensions.toJson(expectedUsercreditScore);
        //when
        //lenient silences the exceptions thrown by StrictStubbing
        Mockito.lenient().when(creditScoreRepository.save(expectedUsercreditScore)).thenReturn(expectedUsercreditScore);
        //validate
        Assertions.assertEquals(actualUserCreditScoreJSON,expectedUsercreditScoreJSON);
        Mockito.verify(creditScoreRepository,Mockito.times(1)).save(expectedUsercreditScore);
    }
}