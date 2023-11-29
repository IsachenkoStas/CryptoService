package com.example.cryptoservice.repository;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.CryptoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByUserId(Long id);

    Optional<Account> findAccountByIdAndUser(Long id, CryptoUser user);

    @Transactional
    @Query("SELECT a FROM accounts a WHERE a.id = ?1")
    Optional<Account> getAccountForUpdate(Long id);
}