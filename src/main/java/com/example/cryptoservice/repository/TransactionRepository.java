package com.example.cryptoservice.repository;

import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.domain.Transaction;
import com.example.cryptoservice.domain.TransactionType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findTransactionByIdAndAccount_User(Long id, CryptoUser user);

    List<Transaction> findByAccount_User(CryptoUser user);

    List<Transaction> findAllByTransactionType(TransactionType transactionType, Pageable pageable);
}