package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.AccountType;
import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.domain.CurrencyCode;
import com.example.cryptoservice.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    AccountRepository accountRepository;

    static List<Account> accountList = null;

    static Account account = null;

    static CryptoUser cryptoUser = null;

    @BeforeAll
    static void beforeAll() {
        accountList = new ArrayList<>();
        account = new Account();
        account.setBalance(BigDecimal.valueOf(100));
        account.setAccountType(AccountType.SAVING);
        account.setCurrencyCode(CurrencyCode.ETH);
        account.setCreated(LocalDateTime.now());
        account.setUser(cryptoUser);
        accountList.add(account);
    }

    @Test
    void getAllUserAccountsTest() {
        Mockito.when(accountRepository.findAll()).thenReturn(accountList);

        List<Account> resultList = accountService.getAllUserAccounts();
        Mockito.verify(accountRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(resultList);
    }
}