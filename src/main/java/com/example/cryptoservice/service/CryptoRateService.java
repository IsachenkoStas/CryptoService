package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.CryptoRate;
import com.example.cryptoservice.domain.CurrencyCode;

public interface CryptoRateService {

    CryptoRate getRate(String base, String target);
}
