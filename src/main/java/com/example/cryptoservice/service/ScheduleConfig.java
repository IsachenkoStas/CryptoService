package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.AccountType;
import com.example.cryptoservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleConfig {

    private final AccountRepository repository;
    private final static String CRON = "*/30 * * * * *";  //20 4 * * *
    private final static Double DEPOSIT_INTEREST = 0.01;

    @Scheduled(cron = CRON)
    public void updateAllDepositAccounts() {
        List<Account> depositAccounts = repository.findAllByAccountType(AccountType.DEPOSIT);
        if (!depositAccounts.isEmpty()) {
            for (Account account : depositAccounts) {
                account.setBalance(account.getBalance().add(account.getBalance().multiply(BigDecimal.valueOf(DEPOSIT_INTEREST))));
                repository.save(account);
            }
        }
    }
}
