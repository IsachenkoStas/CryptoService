package com.example.cryptoservice.controller;

import com.example.cryptoservice.domain.TransactionType;
import com.example.cryptoservice.domain.dto.AccountDto;
import com.example.cryptoservice.domain.dto.DepositDto;
import com.example.cryptoservice.domain.dto.TransactionDetailsDto;
import com.example.cryptoservice.domain.dto.TransactionDto;
import com.example.cryptoservice.domain.dto.TransferDto;
import com.example.cryptoservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final ModelMapper modelMapper;
    private final TransactionService transactionService;

    @GetMapping("/get-all")
    public ResponseEntity<Page<TransactionDto>> getAllTransferTransactions(
            @PageableDefault(value = 10, page = 0, sort = "amount", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) TransactionType transactionType) {
        return new ResponseEntity<>(transactionService.getAllTransactions(pageable, transactionType)
                .map(t -> modelMapper.map(t, TransactionDto.class)), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/{transactionId}")
    public ResponseEntity<TransactionDetailsDto> getTransactionDetailsByUserId(
            @PathVariable Long userId,
            @PathVariable Long transactionId) {
        return new ResponseEntity<>(modelMapper
                .map(transactionService.getTransactionDetails(userId, transactionId), TransactionDetailsDto.class), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<TransactionDetailsDto>> getTransactionsById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(transactionService.getTransactionsByUserId(id).stream()
                .map(acc -> modelMapper.map(acc, TransactionDetailsDto.class)).toList(), HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody @Valid TransferDto transferDto) {
        transactionService.transfer(transferDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@RequestBody @Valid DepositDto depositDto) {
        transactionService.deposit(depositDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody @Valid TransferDto withdrawDto) {
        transactionService.withdraw(withdrawDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/swap")
    public ResponseEntity<Void> swap(@RequestBody @Valid TransferDto swapDto) {
        transactionService.swap(swapDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/my-rewards/{userId}/{accId}")
    public ResponseEntity<AccountDto> getMyRewards(@PathVariable Long userId, @PathVariable Long accId) {
        return new ResponseEntity<>(modelMapper
                .map(transactionService.getMyRewards(userId, accId), AccountDto.class), HttpStatus.OK);
    }
}