package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.CryptoRate;
import com.example.cryptoservice.domain.dto.RateDto;

import java.util.List;

public interface CryptoRateService {

    CryptoRate getCurrencyRate(String base, String target);

    List<CryptoRate> getAllRates();

    void addNewRate(RateDto rateDto);
}