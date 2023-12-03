package com.example.cryptoservice.controller;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.AccountType;
import com.example.cryptoservice.domain.dto.AccountDetailsDto;
import com.example.cryptoservice.domain.dto.AccountDto;
import com.example.cryptoservice.service.AccountService;
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
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final ModelMapper modelMapper;

    @PostMapping("/create-account")
    public ResponseEntity<AccountDto> createAcc(@RequestBody AccountDto accountDto) {
        Account account = accountService.createAcc(modelMapper.map(accountDto, Account.class));
        return new ResponseEntity<>(modelMapper.map(account, AccountDto.class), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Account>> getAll(
            @PageableDefault(value = 5, page = 0, sort = "balance", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) AccountType accountType) {
        return new ResponseEntity<>(accountService.getAllAccounts(pageable, accountType), HttpStatus.OK);
    }

    @GetMapping("/all-mine")
    public ResponseEntity<List<AccountDto>> getAllUserAccounts() {
        return new ResponseEntity<>(accountService.getAllUserAccounts().stream()
                .map(acc -> modelMapper.map(acc, AccountDto.class)).toList(), HttpStatus.OK);
    }

    @GetMapping("/details/{accountId}")
    public ResponseEntity<AccountDetailsDto> getAccountDetails(@PathVariable Long accountId) {
        return new ResponseEntity<>(modelMapper
                .map(accountService.getAccountDetails(accountId), AccountDetailsDto.class), HttpStatus.OK);
    }
}