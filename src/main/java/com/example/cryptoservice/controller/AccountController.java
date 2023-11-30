package com.example.cryptoservice.controller;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.dto.AccountDetailsDto;
import com.example.cryptoservice.domain.dto.AccountDto;
import com.example.cryptoservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final ModelMapper modelMapper;

    @PostMapping("/{userId}")
    public ResponseEntity<AccountDto> createAcc(@PathVariable Long userId, @RequestBody AccountDto accountDto) {
        Account account = accountService.createAcc(modelMapper.map(accountDto, Account.class), userId);
        return new ResponseEntity<>(modelMapper.map(account, AccountDto.class), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAll() {
        List<Account> resultList = accountService.getAllAccounts();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDetailsDto> getAccountDetailsById(@PathVariable Long id) {
        return new ResponseEntity<>(modelMapper.map(accountService.getById(id), AccountDetailsDto.class), HttpStatus.OK);
    }

    @GetMapping("/user_id/{id}")
    public ResponseEntity<List<AccountDto>> getAccountsByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.getAccsByUserId(id).stream()
                .map(acc -> modelMapper.map(acc, AccountDto.class)).toList(), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/accounts/{accountId}")
    public ResponseEntity<AccountDetailsDto> getAccDetailsByUserId(@PathVariable Long userId, @PathVariable Long accountId) {
        return new ResponseEntity<>(modelMapper
                .map(accountService.getAccountDetails(userId, accountId), AccountDetailsDto.class), HttpStatus.OK);
    }

/*    @DeleteMapping("/delete/{userId}/{accountId}")
    public ResponseEntity<HttpStatus> deleteAcc(@PathVariable Long userId, @PathVariable Long accountId) {
        return new ResponseEntity<>(service.deleteAccFromUserById(userId, accountId) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }*/
}