package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.exception_resolver.AccountNotFoundException;
import com.example.cryptoservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final CryptoUserService userService;

    @Override
    public Account createAcc(Account account, Long userId) {
        CryptoUser user = userService.findById(userId);
        account.setUser(user);
        return repository.save(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    @Override
    public Account getById(Long id) {
        return repository.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public List<Account> getAccsByUserId(Long id) {
        return repository.findAccountsByUserId(id);
    }

    @Override
    public Account getAccountDetails(Long userId, Long accountId) {
        CryptoUser user = userService.findById(userId);
        return repository.findAccountByIdAndUser(accountId, user).orElseThrow(AccountNotFoundException::new);
    }

/*    @Override
    public boolean deleteAccFromUserById(Long userId, Long accountId) {
        List<Account> accounts = getAccsByUserId(userId);
        for (Account acc : accounts) {
            if (!Objects.equals(acc.getId(), accountId)){
                throw new AccountNotFoundException();
            }
        }
        repository.deleteById(accountId);
        return true;
    }*/
}