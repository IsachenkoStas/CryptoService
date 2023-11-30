package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.CryptoUser;

import java.util.List;

public interface CryptoUserService {
    List<CryptoUser> getAllUsers();

    CryptoUser findById(Long id);

    Long countByLogin(String login);
}
