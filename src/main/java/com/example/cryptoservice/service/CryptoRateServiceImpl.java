package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.CryptoRate;
import com.example.cryptoservice.exception_resolver.CryptoRateNotFoundException;
import com.example.cryptoservice.repository.CryptoRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CryptoRateServiceImpl implements CryptoRateService {

    private final CryptoRateRepository cryptoRateRepository;

    @Override
    public CryptoRate getCurrencyRate(String base, String target) {
        return cryptoRateRepository.findByBaseCurrencyAndTargetCurrency(base, target)
                .orElseThrow(() -> new CryptoRateNotFoundException("Crypto rate is not found"));
    }

    @Override
    public List<CryptoRate> getAllRates() {
        return cryptoRateRepository.findAll();
    }
}
