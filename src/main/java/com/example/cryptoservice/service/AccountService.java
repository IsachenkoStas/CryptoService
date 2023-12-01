package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Account;

import java.util.List;

public interface AccountService {
    Account createAcc(Account account, Long userId);

    List<Account> getAllAccounts();

    Account getById(Long id);

    List<Account> getAccsByUserId(Long id);

    Account getAccountDetails(Long userId, Long accountId);

    /*    boolean deleteAccFromUserById(Long userId, Long accountId);*/
}
