package com.example.cryptoservice.domain.dto;

import com.example.cryptoservice.domain.AccountType;
import com.example.cryptoservice.domain.CurrencyCode;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountDetailsDto {
        private BigDecimal balance;
        private CurrencyCode currencyCode;
        private AccountType accountType;
        private LocalDateTime created;
        private UserDto user;
}
