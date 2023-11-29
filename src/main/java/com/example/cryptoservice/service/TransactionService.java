package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Transaction;
import com.example.cryptoservice.domain.dto.DepositDto;
import com.example.cryptoservice.domain.dto.TransferDto;
import com.example.cryptoservice.domain.dto.WithdrawDto;

import java.util.List;

public interface TransactionService {

    Transaction getTransactionDetails(Long userId, Long transactionId);

    List<Transaction> getTransactionsByUserId(Long id);

    void transfer(TransferDto transferDto);

    void deposit(DepositDto deposit);

    void withdraw(WithdrawDto withdraw);

    void swap(TransferDto swap);
}