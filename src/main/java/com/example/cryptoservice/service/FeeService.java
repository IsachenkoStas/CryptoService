package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.CurrencyCode;
import com.example.cryptoservice.domain.TransactionFee;
import com.example.cryptoservice.domain.dto.TransferDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface FeeService {

    BigDecimal fee(TransferDto transfer, Account accFrom);

    Page<TransactionFee> getAll(Pageable pageable, CurrencyCode currencyCode);
}