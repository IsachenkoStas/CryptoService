package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.TransactionFee;
import com.example.cryptoservice.domain.dto.TransferDto;

import java.math.BigDecimal;

public interface FeeService {

    BigDecimal fee(TransferDto transfer, Account accFrom);
}
