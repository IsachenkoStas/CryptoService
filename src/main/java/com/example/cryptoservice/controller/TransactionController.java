package com.example.cryptoservice.controller;

import com.example.cryptoservice.domain.dto.*;
import com.example.cryptoservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final ModelMapper modelMapper;
    private final TransactionService service;

    @GetMapping("/users/{userId}/{transactionId}")
    public ResponseEntity<TransactionDetailsDto> getTransactionDetailsByUserId(@PathVariable Long userId, @PathVariable Long transactionId) {
        return new ResponseEntity<>(modelMapper
                .map(service.getTransactionDetails(userId, transactionId), TransactionDetailsDto.class), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<TransactionDetailsDto>> getTransactionsById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.getTransactionsByUserId(id).stream()
                .map(acc -> modelMapper.map(acc, TransactionDetailsDto.class)).toList(), HttpStatus.OK);
    }

    @PostMapping("/transfers")
    public ResponseEntity<Void> transfer(@RequestBody @Valid TransferDto transferDto) {
        service.transfer(transferDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/deposits")
    public ResponseEntity<Void> deposit(@RequestBody @Valid DepositDto depositDto) {
        service.deposit(depositDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody @Valid WithdrawDto withdraw) {
        service.withdraw(withdraw);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/swap")
    public ResponseEntity<Void> swap(@RequestBody @Valid TransferDto swapDto) {
        service.swap(swapDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
