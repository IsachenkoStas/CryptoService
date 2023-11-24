package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.repository.CryptoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoUserServiceImpl implements CryptoUserService{

    private final CryptoUserRepository cryptoUserRepository;

    @Override
    public List<CryptoUser> getAllUsers() {
        return cryptoUserRepository.findAll();
    }
}
