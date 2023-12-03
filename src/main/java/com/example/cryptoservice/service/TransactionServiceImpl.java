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
import com.example.cryptoservice.security.repository.SecurityCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final CryptoUserService userService;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final CryptoRateService cryptoRateService;
    private final TransactionValidator transactionValidation;
    private final SecurityCredentialsRepository securityCredentialsRepository;
    private final FeeService feeService;
    public static BigDecimal FEE_INTEREST = new BigDecimal("0.01");

    @Override
    public Page<Transaction> getAllTransactions(Pageable pageable, TransactionType transactionType) {
        Page<Transaction> transactions;
        if (transactionType != null) {
            transactions = transactionRepository.findAllByTransactionType(transactionType, pageable);
        } else {
            transactions = transactionRepository.findAll(pageable);
        }
        return transactions;
    }

    @Override
    public Transaction getTransactionDetails(Long transactionId) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = securityCredentialsRepository.findUserIdByLogin(userLogin);
        CryptoUser user = userService.findById(userId);
        return transactionRepository.findTransactionByIdAndAccount_User(transactionId, user)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with id " + transactionId + " not found"));
    }

    @Override
    public List<Transaction> getAllUserTransactions() {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = securityCredentialsRepository.findUserIdByLogin(userLogin);
        CryptoUser user = userService.findById(userId);
        return transactionRepository.findByAccount_User(user);
    }

    //TODO: ONE TEST PER ONE SERVICE

    @Transactional
    @Override
    public void transfer(TransferDto transfer) {
        Account accountFrom = accountService.getAccountDetails(transfer.getAccIdFrom());
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
        Account account = accountService.getAccountDetails(deposit.getAccId());
        transactionValidation.validateDeposit(deposit, account);

        account.setBalance(account.getBalance().subtract(deposit.getAmount()));

        Account depAcc = null;
        try {
            depAcc = accountRepository
                    .findByCurrencyCodeAndAccountTypeAndUser(account.getCurrencyCode(), AccountType.DEPOSIT, account.getUser());
            if (depAcc != null) {
                depAcc.setBalance(deposit.getAmount().add(depAcc.getBalance()));
            }
        } catch (NullPointerException e) {
            e.getMessage();
        }
        if (depAcc == null) {
            Account acc = Account.builder()
                    .accountType(AccountType.DEPOSIT)
                    .balance(deposit.getAmount())
                    .currencyCode(account.getCurrencyCode())
                    .user(account.getUser())
                    .build();
            accountRepository.save(acc);
        }

        Transaction depositTransaction = Transaction.builder()
                .amount(deposit.getAmount())
                .transactionType(TransactionType.DEPOSIT)
                .account(account)
                .created(LocalDateTime.now())
                .build();
        transactionRepository.save(depositTransaction);
    }

    @Transactional
    @Override
    public void withdraw(TransferDto withdraw) {
        Account depAccount = accountService.getAccountDetails(withdraw.getAccIdFrom());
        Account withdrawAccount = accountService.getAccountDetails(withdraw.getAccIdTo());
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
        Account accFrom = accountService.getAccountDetails(swap.getAccIdFrom());
        Account accTo = accountService.getAccountDetails(swap.getAccIdTo());
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
    public Account getMyRewards(Long accId) {
        Account account = accountService.getAccountDetails(accId);
        if (!Objects.equals(account.getAccountType(), AccountType.DEPOSIT)) {
            throw new NotDepositAccountException("Account with id: " + accId + " is not deposit account.");
        }
        return account;
    }
}