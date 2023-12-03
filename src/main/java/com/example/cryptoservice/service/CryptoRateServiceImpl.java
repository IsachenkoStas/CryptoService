package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.CryptoRate;
import com.example.cryptoservice.domain.Role;
import com.example.cryptoservice.domain.dto.RateDto;
import com.example.cryptoservice.exception_resolver.CryptoRateNotFoundException;
import com.example.cryptoservice.exception_resolver.NoAccessByLoginException;
import com.example.cryptoservice.exception_resolver.UserNotFoundException;
import com.example.cryptoservice.repository.CryptoRateRepository;
import com.example.cryptoservice.security.domain.SecurityCredentials;
import com.example.cryptoservice.security.repository.SecurityCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CryptoRateServiceImpl implements CryptoRateService {

    private final CryptoRateRepository cryptoRateRepository;
    private final SecurityCredentialsRepository securityCredentialsRepository;
    private final ModelMapper modelMapper;

    @Override
    public CryptoRate getCurrencyRate(String base, String target) {
        return cryptoRateRepository.findByBaseCurrencyAndTargetCurrency(base, target)
                .orElseThrow(() -> new CryptoRateNotFoundException("Crypto rate is not found"));
    }

    @Override
    public List<CryptoRate> getAllRates() {
        return cryptoRateRepository.findAll();
    }

    @Override
    public void addNewRate(RateDto rateDto) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        SecurityCredentials userCredentials = securityCredentialsRepository.getByLogin(userLogin)
                .orElseThrow(() -> new UserNotFoundException("User with login " + userLogin + " not found"));
        if (!Objects.equals(userCredentials.getRole(), Role.ADMIN)) {
            throw new NoAccessByLoginException(userLogin);
        }
        CryptoRate rate = new CryptoRate();
        modelMapper.map(rateDto, rate);
        cryptoRateRepository.save(rate);
    }
}