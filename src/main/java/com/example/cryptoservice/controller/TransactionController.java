package com.example.cryptoservice.controller;

import com.example.cryptoservice.domain.dto.TransactionDetailsDto;
import com.example.cryptoservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final ModelMapper modelMapper;
    private final TransactionService service;

    @GetMapping("/users/{userId}/{transactionId}")
    public ResponseEntity<TransactionDetailsDto> getAccDetailsByUserId(@PathVariable Long userId, @PathVariable Long transactionId) {
        return new ResponseEntity<>(modelMapper
                .map(service.getTransactionDetails(userId, transactionId), TransactionDetailsDto.class), HttpStatus.OK);
    }
}
