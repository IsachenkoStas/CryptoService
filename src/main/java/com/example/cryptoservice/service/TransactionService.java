package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction getTransactionDetails(Long userId, Long transactionId);

    List<Transaction> getTransactionsByUserId(Long id);
}