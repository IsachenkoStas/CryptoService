package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Transaction;

public interface TransactionService {

    Transaction getTransactionDetails(Long userId, Long transactionId);
}