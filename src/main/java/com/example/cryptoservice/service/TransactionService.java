package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Transaction;
import com.example.cryptoservice.domain.dto.DepositDto;
import com.example.cryptoservice.domain.dto.TransferDto;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    void createTransaction(Transaction transaction, Long acc_id);

    Transaction getTransactionDetails(Long userId, Long transactionId);

    List<Transaction> getTransactionsByUserId(Long id);

    Optional<Transaction> getDepositTransactionByAccId(Long id);

    void transfer(TransferDto transferDto);

    void deposit(DepositDto deposit);

    void withdraw(DepositDto withdraw);
}