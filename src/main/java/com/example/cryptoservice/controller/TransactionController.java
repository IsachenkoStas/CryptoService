package com.example.cryptoservice.controller;

import com.example.cryptoservice.domain.CurrencyCode;
import com.example.cryptoservice.domain.Transaction;
import com.example.cryptoservice.domain.TransactionFee;
import com.example.cryptoservice.domain.TransactionType;
import com.example.cryptoservice.domain.dto.AccountDto;
import com.example.cryptoservice.domain.dto.DepositDto;
import com.example.cryptoservice.domain.dto.TransactionDetailsDto;
import com.example.cryptoservice.domain.dto.TransferDto;
import com.example.cryptoservice.service.FeeService;
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
    private final FeeService feeService;

    @GetMapping("/get-all")
    public ResponseEntity<Page<Transaction>> getAllTransferTransactions(
            @PageableDefault(value = 5, page = 0, sort = "amount", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) TransactionType transactionType) {
        return new ResponseEntity<>(transactionService.getAllTransactions(pageable, transactionType), HttpStatus.OK);
    }

    @GetMapping("/details/{transactionId}")
    public ResponseEntity<TransactionDetailsDto> getTransactionDetails(@PathVariable Long transactionId) {
        return new ResponseEntity<>(modelMapper
                .map(transactionService.getTransactionDetails(transactionId), TransactionDetailsDto.class), HttpStatus.OK);
    }

    @GetMapping("/all-mine")
    public ResponseEntity<List<TransactionDetailsDto>> getAllUserTransactions() {
        return new ResponseEntity<>(transactionService.getAllUserTransactions().stream()
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

    @GetMapping("/my-rewards/{accId}")
    public ResponseEntity<AccountDto> getMyRewards(@PathVariable Long accId) {
        return new ResponseEntity<>(modelMapper
                .map(transactionService.getMyRewards(accId), AccountDto.class), HttpStatus.OK);
    }

    @GetMapping("/all-fees")
    public ResponseEntity<Page<TransactionFee>> getAllFees(
            @PageableDefault(value = 5, page = 0, sort = "amount", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) CurrencyCode currencyCode) {
        return new ResponseEntity<>(feeService.getAll(pageable, currencyCode), HttpStatus.OK);
    }
}