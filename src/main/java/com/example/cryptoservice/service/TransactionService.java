package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.Transaction;
import com.example.cryptoservice.domain.TransactionType;
import com.example.cryptoservice.domain.dto.DepositDto;
import com.example.cryptoservice.domain.dto.TransferDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {

    Transaction getTransactionDetails(Long transactionId);

    List<Transaction> getAllUserTransactions();

    void transfer(TransferDto transfer);

    void deposit(DepositDto deposit);

    void withdraw(TransferDto withdraw);

    void swap(TransferDto swap);

    Account getMyRewards(Long accId);

    Page<Transaction> getAllTransactions(Pageable pageable, TransactionType transactionType);
}