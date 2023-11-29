package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Transaction;
import com.example.cryptoservice.domain.dto.TransferDto;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction, Long acc_id);

    Transaction getTransactionDetails(Long userId, Long transactionId);

    List<Transaction> getTransactionsByUserId(Long id);

    void transfer(TransferDto transferDto);
}