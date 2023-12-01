package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.AccountType;
import com.example.cryptoservice.domain.CryptoRate;
import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.domain.Transaction;
import com.example.cryptoservice.domain.TransactionType;
import com.example.cryptoservice.domain.dto.DepositDto;
import com.example.cryptoservice.domain.dto.TransferDto;
import com.example.cryptoservice.exception_resolver.NotDepositAccountException;
import com.example.cryptoservice.exception_resolver.TransactionNotFoundException;
import com.example.cryptoservice.repository.AccountRepository;
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
    private final AccountRepository accountRepository;
    private final CryptoRateService cryptoRateService;
    private final TransactionValidator transactionValidation;
    private final FeeService feeService;
    public static BigDecimal FEE_INTEREST = BigDecimal.valueOf(0.01);

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
        transactionValidation.validateTransfer(transfer, accountFrom, accountTo);

        BigDecimal feeAmount = feeService.fee(transfer, accountFrom);

        accountFrom.setBalance(accountFrom.getBalance().subtract(transfer.getAmount().add(feeAmount)));
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
        transactionValidation.validateDeposit(deposit, depAccount);

        depAccount.setBalance(depAccount.getBalance().subtract(deposit.getAmount()));
        Account acc = Account.builder()
                .accountType(AccountType.DEPOSIT)
                .balance(deposit.getAmount())
                .currencyCode(depAccount.getCurrencyCode())
                .user(userService.findById(deposit.getUserId()))
                .build();
        accountRepository.save(acc);

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
    public void withdraw(TransferDto withdraw) {
        Account depAccount = accountService.getAccountDetails(withdraw.getUserId(), withdraw.getAccIdFrom());
        Account withdrawAccount = accountService.getAccountDetails(withdraw.getUserId(), withdraw.getAccIdTo());
        transactionValidation.validateWithdraw(withdraw, withdrawAccount, depAccount);

        withdrawAccount.setBalance(withdrawAccount.getBalance().add(withdraw.getAmount()));
        depAccount.setBalance(depAccount.getBalance().subtract(withdraw.getAmount()));
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
        transactionValidation.validateSwap(accFrom, accTo, swap);

        CryptoRate targetRate = cryptoRateService.getCurrencyRate(accFrom.getCurrencyCode().toString(), accTo.getCurrencyCode().toString());
        BigDecimal rate = targetRate.getRate();

        BigDecimal feeAmount = feeService.fee(swap, accFrom);

        accFrom.setBalance(accFrom.getBalance().subtract(swap.getAmount().add(feeAmount)));
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

    @Override
    public Account checkMyRewards(Long userId, Long accId) {
        Account account = accountService.getAccountDetails(userId, accId);
        if (!Objects.equals(account.getAccountType(), AccountType.DEPOSIT)) {
            throw new NotDepositAccountException("Account with id: " + accId + " is not deposit account.");
        }
        return account;
    }
}