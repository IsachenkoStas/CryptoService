package com.example.cryptoservice.repository;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.AccountType;
import com.example.cryptoservice.domain.CryptoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUserId(Long id);

    Optional<Account> findAccountByIdAndUser(Long id, CryptoUser user);

    List<Account> findAllByAccountType(AccountType accountType);

}