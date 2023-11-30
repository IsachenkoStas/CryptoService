package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.Transaction;
import com.example.cryptoservice.domain.dto.DepositDto;
import com.example.cryptoservice.domain.dto.TransferDto;

import java.util.List;

public interface TransactionService {

    Transaction getTransactionDetails(Long userId, Long transactionId);

    List<Transaction> getTransactionsByUserId(Long id);

    void transfer(TransferDto transfer);

    void deposit(DepositDto deposit);

    void withdraw(TransferDto withdraw);

    void swap(TransferDto swap);

    Account checkMyRewards(Long userId, Long accId);
}