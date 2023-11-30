package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.*;
import com.example.cryptoservice.domain.dto.DepositDto;
import com.example.cryptoservice.domain.dto.TransferDto;
import com.example.cryptoservice.domain.dto.WithdrawDto;
import com.example.cryptoservice.exception_resolver.NotEnoughMoneyException;
import com.example.cryptoservice.exception_resolver.NotEqualCurrencyException;
import com.example.cryptoservice.exception_resolver.TransactionNotFoundException;
import com.example.cryptoservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final CryptoUserService userService;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final CryptoRateService cryptoRateService;

    @Override
    public Transaction getTransactionDetails(Long userId, Long transactionId) {
        CryptoUser user = userService.findById(userId);
        return transactionRepository.findTransactionByIdAndAccount_User(transactionId, user)
                .orElseThrow(() -> new TransactionNotFoundException("Account with id " + transactionId + " not found"));
    }

    @Override
    public List<Transaction> getTransactionsByUserId(Long id) {
        CryptoUser user = userService.findById(id);
        return transactionRepository.findByAccount_User(user);
    }

    @Transactional
    @Override
    public void transfer(TransferDto transfer) {
        Account accountFrom = accountService.getAccountDetails(transfer.getUserId(), transfer.getAccIdFrom());
        Account accountTo = accountService.getById(transfer.getAccIdTo());
        if (accountFrom.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new NotEnoughMoneyException("Account with id:" + accountFrom.getId() + " does not have enough balance to transfer.");
        }
        if (!Objects.equals(accountFrom.getCurrencyCode(), accountTo.getCurrencyCode())) {
            throw new NotEqualCurrencyException("Account with id: " + accountFrom.getId() + " has a different currency code compared to" + accountTo.getId());
        }

        accountFrom.setBalance(accountFrom.getBalance().subtract(transfer.getAmount()));
        accountTo.setBalance(accountTo.getBalance().add(transfer.getAmount()));

        Transaction transactionAccFrom = Transaction.builder()
                .amount(transfer.getAmount())
                .transactionType(TransactionType.TRANSFER)
                .account(accountFrom)
                .created(LocalDateTime.now())
                .build();
        transactionRepository.save(transactionAccFrom);

        Transaction transactionAccTo = Transaction.builder()
                .amount(transfer.getAmount())
                .transactionType(TransactionType.TRANSFER)
                .account(accountTo)
                .created(LocalDateTime.now())
                .build();
        transactionRepository.save(transactionAccTo);
    }

    @Transactional
    @Override
    public void deposit(DepositDto deposit) {
        Account depAccount = accountService.getAccountDetails(deposit.getUserId(), deposit.getAccId());
        if (depAccount.getBalance().compareTo(deposit.getAmount()) < 0) {
            throw new NotEnoughMoneyException("Account with id:" + depAccount.getId() + " does not have enough balance to transfer.");
        }
        depAccount.setBalance(depAccount.getBalance().subtract(deposit.getAmount()));
        Transaction depositTransaction = Transaction.builder()
                .amount(deposit.getAmount())
                .transactionType(TransactionType.DEPOSIT)
                .account(depAccount)
                .created(LocalDateTime.now())
                .build();
        transactionRepository.save(depositTransaction);
    }

    @Transactional
    @Override
    public void withdraw(WithdrawDto withdraw) {
        Account withdrawAccount = accountService.getAccountDetails(withdraw.getUserId(), withdraw.getAccId());
        Transaction withdrawTransaction = getTransactionDetails(withdraw.getUserId(), withdrawAccount.getId());
        if (withdrawTransaction.getAmount().compareTo(withdraw.getAmount()) < 0) {
            throw new NotEnoughMoneyException("Wrong amount of money");
        }
        withdrawAccount.setBalance(withdrawAccount.getBalance().add(withdraw.getAmount()));
        withdrawTransaction.setAmount(withdrawTransaction.getAmount().subtract(withdraw.getAmount()));

        Transaction transaction = Transaction.builder()
                .amount(withdraw.getAmount())
                .transactionType(TransactionType.WITHDRAW)
                .account(withdrawAccount)
                .created(LocalDateTime.now())
                .build();
        transactionRepository.save(transaction);
    }

    @Transactional
    @Override
    public void swap(TransferDto swap) {
        Account accFrom = accountService.getAccountDetails(swap.getUserId(), swap.getAccIdFrom());
        Account accTo = accountService.getAccountDetails(swap.getUserId(), swap.getAccIdTo());
        CryptoRate targetRate = cryptoRateService.getCurrencyRate(accFrom.getCurrencyCode().toString(), accTo.getCurrencyCode().toString());
        BigDecimal rate = targetRate.getRate();
        if (accFrom.getBalance().compareTo(swap.getAmount()) < 0) {
            throw new NotEnoughMoneyException("Account with id:" + accFrom.getId() + " does not have enough balance to transfer.");
        }
        accFrom.setBalance(accFrom.getBalance().subtract(swap.getAmount()));
        Transaction transactionAccFrom = Transaction.builder()
                .amount(swap.getAmount())
                .transactionType(TransactionType.SWAP)
                .account(accFrom)
                .created(LocalDateTime.now())
                .build();
        transactionRepository.save(transactionAccFrom);

        accTo.setBalance(accTo.getBalance().add(swap.getAmount().multiply(rate)));
        Transaction transactionAccTo = Transaction.builder()
                .amount(swap.getAmount())
                .transactionType(TransactionType.SWAP)
                .account(accTo)
                .created(LocalDateTime.now())
                .build();
        transactionRepository.save(transactionAccTo);
    }
}