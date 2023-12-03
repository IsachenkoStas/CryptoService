package com.example.cryptoservice.repository;

import com.example.cryptoservice.domain.CurrencyCode;
import com.example.cryptoservice.domain.TransactionFee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionFeeRepository extends JpaRepository<TransactionFee, Long> {
    TransactionFee findByCurrencyCode(CurrencyCode currencyCode);

    Page<TransactionFee> findAllByCurrencyCode(Pageable pageable, CurrencyCode currencyCode);
}
