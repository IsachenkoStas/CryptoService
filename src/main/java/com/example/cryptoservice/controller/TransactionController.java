package com.example.cryptoservice.controller;

import com.example.cryptoservice.domain.dto.DepositDto;
import com.example.cryptoservice.domain.dto.TransactionDetailsDto;
import com.example.cryptoservice.domain.dto.TransferDto;
import com.example.cryptoservice.domain.dto.WithdrawDto;
import com.example.cryptoservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody @Valid TransferDto transferDto) {
        service.transfer(transferDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@RequestBody @Valid DepositDto depositDto) {
        service.deposit(depositDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody @Valid WithdrawDto withdrawDto) {
        service.withdraw(withdrawDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/swap")
    public ResponseEntity<Void> swap(@RequestBody @Valid TransferDto swapDto) {
        service.swap(swapDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/my-rewards/{userId}/{accId}")
    private ResponseEntity<BigDecimal> checkMyRewards(@PathVariable Long userId, @PathVariable Long accId) {
        return new ResponseEntity<>(service.checkMyRewards(userId, accId), HttpStatus.OK);
    }
}