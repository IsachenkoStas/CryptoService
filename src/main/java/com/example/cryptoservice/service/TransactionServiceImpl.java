package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.domain.Transaction;
import com.example.cryptoservice.domain.TransactionType;
import com.example.cryptoservice.domain.dto.TransactionDto;
import com.example.cryptoservice.domain.dto.TransferDto;
import com.example.cryptoservice.exception_resolver.AccountNotFoundException;
import com.example.cryptoservice.exception_resolver.NotEnoughMoneyException;
import com.example.cryptoservice.exception_resolver.TransactionNotFoundException;
import com.example.cryptoservice.repository.AccountRepository;
import com.example.cryptoservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final CryptoUserService userService;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    @Override
    public Transaction createTransaction(Transaction transaction, Long acc_id) {
        Account account = accountService.getById(acc_id);
        transaction.setAccount(account);
        return transactionRepository.save(transaction);
    }

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

        //TODO: ПРОВЕРИТЬ ОДИНАКОВЫЙ ЛИ CURRENCY_CODE

        Account accountFrom = accountRepository.getAccountForUpdate(transfer.getAccIdFrom())
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + transfer.getAccIdFrom() + " not found"));
        Account accountTo = accountRepository.getAccountForUpdate(transfer.getAccIdTo())
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + transfer.getAccIdTo() + " not found"));

        if (accountFrom.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new NotEnoughMoneyException("Account with id:" + accountFrom.getId() + " does not have enough balance to transfer.");
        }
//        if (accountFrom.getCurrencyCode().equals(accountTo.getCurrencyCode()))

            accountFrom.setBalance(accountFrom.getBalance().subtract(transfer.getAmount()));
        accountTo.setBalance(accountTo.getBalance().add(transfer.getAmount()));

        TransactionDto transaction = new TransactionDto(transfer.getAmount(), TransactionType.TRANSFER, accountFrom, LocalDateTime.now());
        createTransaction(modelMapper.map(transaction, Transaction.class), transfer.getAccIdFrom());
    }
}