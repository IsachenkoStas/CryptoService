package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.Transaction;
import com.example.cryptoservice.domain.TransactionType;
import com.example.cryptoservice.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    TransactionRepository transactionRepository;

    static List<Transaction> transactionList = null;

    static Transaction transaction = null;

    static Account account = null;

    @BeforeAll
    static void beforeAll() {
        transactionList = new ArrayList<>();
        transaction = new Transaction();
        transaction.setId(10L);
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setAccount(account);
        transactionList.add(transaction);
    }

    @Test
    void getAllUserAccountsTest() {
        Mockito.when(transactionRepository.findAll()).thenReturn(transactionList);

        List<Transaction> resultList = transactionService.getAllUserTransactions();
        Mockito.verify(transactionRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(resultList);
    }
}