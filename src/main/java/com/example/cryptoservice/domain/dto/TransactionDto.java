package com.example.cryptoservice.domain.dto;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private BigDecimal amount;
    private TransactionType transactionType;
    private Account account;
    private LocalDateTime created;
}
