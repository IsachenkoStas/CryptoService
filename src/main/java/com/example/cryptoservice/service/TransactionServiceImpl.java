package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.domain.Transaction;
import com.example.cryptoservice.exception_resolver.TransactionNotFoundException;
import com.example.cryptoservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final CryptoUserService userService;
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction getTransactionDetails(Long userId, Long transactionId) {
        CryptoUser user = userService.findById(userId);

        return transactionRepository.findTransactionByIdAndAccount_User(transactionId, user)
                .orElseThrow(() -> new TransactionNotFoundException("Account with id " + transactionId + " not found"));
    }
}