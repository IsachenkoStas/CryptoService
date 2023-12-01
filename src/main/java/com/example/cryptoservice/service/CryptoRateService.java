package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.CryptoRate;

import java.util.List;

public interface CryptoRateService {

    CryptoRate getCurrencyRate(String base, String target);

    List<CryptoRate> getAllRates();
}
