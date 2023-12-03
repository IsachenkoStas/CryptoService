package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.exception_resolver.UserNotFoundException;
import com.example.cryptoservice.repository.CryptoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoUserServiceImpl implements CryptoUserService {

    private final CryptoUserRepository cryptoUserRepository;

    @Override
    public List<CryptoUser> getAllUsers() {
        return cryptoUserRepository.findAll();
    }

    @Override
    public CryptoUser findById(Long id) {
        return cryptoUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public Long countByLogin(String login) {
        return cryptoUserRepository.countByLogin(login);
    }
}