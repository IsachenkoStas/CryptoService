package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.domain.dto.UserDto;

import java.util.List;

public interface CryptoUserService {
    List<CryptoUser> getAllUsers();

    CryptoUser findById(Long id);

    void updateUser(UserDto userDto);

    boolean deleteUser(Long userId);

    CryptoUser getUser();
}