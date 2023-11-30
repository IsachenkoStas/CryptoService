package com.example.cryptoservice.controller;


import com.example.cryptoservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/deposits")
public class DepositController {

    private final AccountService accountService;

    @PostMapping("/update")
    public ResponseEntity<Void> depositUpdate() {
        accountService.updateAllDepositAccounts();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
