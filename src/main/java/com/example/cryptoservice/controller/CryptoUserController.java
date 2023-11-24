package com.example.cryptoservice.controller;

import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.service.CryptoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crypto-user")
public class CryptoUserController {

    private final CryptoUserService cryptoUserService;

    @GetMapping
    public ResponseEntity<List<CryptoUser>> getAll() {
        List<CryptoUser> resultList = cryptoUserService.getAllUsers();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }
}