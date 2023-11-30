package com.example.cryptoservice.domain.dto;

import com.example.cryptoservice.domain.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDetailsDto {
    private BigDecimal amount;
    private TransactionType transactionType;
    private LocalDateTime created;
}
