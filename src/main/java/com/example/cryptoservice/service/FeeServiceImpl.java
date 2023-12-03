package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.CurrencyCode;
import com.example.cryptoservice.domain.TransactionFee;
import com.example.cryptoservice.domain.dto.TransferDto;
import com.example.cryptoservice.repository.TransactionFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        TransactionFee fee = feeRepository.findByCurrencyCode(accFrom.getCurrencyCode());
        fee.setAmount(fee.getAmount().add(feeAmount));
        fee.setLastUpdated(LocalDateTime.now());

        feeRepository.save(fee);
        return feeAmount;
    }

    @Override
    public Page<TransactionFee> getAll(Pageable pageable, CurrencyCode currencyCode) {
        Page<TransactionFee> fees;
        if (currencyCode != null) {
            fees = feeRepository.findAllByCurrencyCode(pageable, currencyCode);
        } else {
            fees = feeRepository.findAll(pageable);
        }
        return fees;
    }

    //TODO: GETALL METHOD FOR ADMIN
}