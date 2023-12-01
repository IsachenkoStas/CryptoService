package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.TransactionFee;
import com.example.cryptoservice.domain.dto.TransferDto;
import com.example.cryptoservice.repository.TransactionFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class FeeServiceImpl implements FeeService {

    private final TransactionFeeRepository feeRepository;

    @Override
    public BigDecimal fee(TransferDto transfer, Account accFrom) {
        BigDecimal feeAmount = transfer.getAmount().multiply(TransactionServiceImpl.FEE_INTEREST);

        TransactionFee fee = feeRepository.findByCurrency(accFrom.getCurrencyCode().toString());
        fee.setAmount(fee.getAmount().add(feeAmount));
        fee.setLast_updated(LocalDateTime.now());

        feeRepository.save(fee);
        return feeAmount;
    }
}