package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.CryptoRate;
import com.example.cryptoservice.repository.CryptoRateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CryptoRateServiceImplTest {

    @InjectMocks
    CryptoRateService cryptoRateService;

    @Mock
    CryptoRateRepository cryptoRateRepository;

    static List<CryptoRate> rateList = null;

    static CryptoRate cryptoRate = null;

    @BeforeAll
    static void beforeAll() {
        rateList = new ArrayList<>();
        cryptoRate = new CryptoRate();
        cryptoRate.setId(10L);
        cryptoRate.setRate(BigDecimal.valueOf(0.5));
        cryptoRate.setBaseCurrency("BTC");
        cryptoRate.setTargetCurrency("ETH");
        rateList.add(cryptoRate);
    }

    @Test
    void getAllRates() {
        Mockito.when(cryptoRateRepository.findAll()).thenReturn(rateList);

        List<CryptoRate> resultList = cryptoRateService.getAllRates();
        Mockito.verify(cryptoRateRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(resultList);
    }
}