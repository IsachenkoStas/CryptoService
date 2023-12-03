package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.AccountType;
import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.domain.Role;
import com.example.cryptoservice.exception_resolver.AccountNotFoundException;
import com.example.cryptoservice.exception_resolver.NoAccessByLoginException;
import com.example.cryptoservice.exception_resolver.UserNotFoundException;
import com.example.cryptoservice.repository.AccountRepository;
import com.example.cryptoservice.security.domain.SecurityCredentials;
import com.example.cryptoservice.security.repository.SecurityCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final CryptoUserService userService;
    private final SecurityCredentialsRepository securityCredentialsRepository;

    @Override
    public Account createAcc(Account account) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = securityCredentialsRepository.findUserIdByLogin(userLogin);
        CryptoUser user = userService.findById(userId);
        account.setUser(user);
        account.setCreated(LocalDateTime.now());
        return repository.save(account);
    }

    @Override
    public Page<Account> getAllAccounts(Pageable pageable, AccountType accountType) {
        Page<Account> accounts;
        if (accountType != null) {
            accounts = repository.findAllByAccountType(pageable, accountType);
        } else {
            accounts = repository.findAll(pageable);
        }
        return accounts;
    }

    @Override
    public Account getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + id + " not found"));
    }

    @Override
    public List<Account> getAllUserAccounts() {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = securityCredentialsRepository.findUserIdByLogin(userLogin);
        return repository.findByUserId(userId);
    }

    @Override
    public Account getAccountDetails(Long accountId) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = securityCredentialsRepository.findUserIdByLogin(userLogin);
        CryptoUser user = userService.findById(userId);
        return repository.findAccountByIdAndUser(accountId, user)
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + accountId + " not found"));
    }

    @Override
    public boolean deleteAcc(Long accountId) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        SecurityCredentials userCredentials = securityCredentialsRepository.getByLogin(userLogin)
                .orElseThrow(() -> new UserNotFoundException("User with login " + userLogin + " not found"));
        if (!Objects.equals(userCredentials.getRole(), Role.ADMIN)) {
            throw new NoAccessByLoginException(userLogin);
        }
        repository.deleteById(accountId);
        return true;
    }
}