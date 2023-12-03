package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.AccountType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    Account createAcc(Account account);

    Account getById(Long id);

    List<Account> getAllUserAccounts();

    Account getAccountDetails(Long accountId);

    Page<Account> getAllAccounts(Pageable pageable, AccountType accountType);

    boolean deleteAcc(Long accountId);
}