package com.example.cryptoservice.domain.dto;

import com.example.cryptoservice.domain.AccountType;
import com.example.cryptoservice.domain.CurrencyCode;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {
        private BigDecimal balance;
        private CurrencyCode currencyCode;
        private AccountType accountType;
}