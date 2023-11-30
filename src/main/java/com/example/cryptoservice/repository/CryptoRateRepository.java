package com.example.cryptoservice.repository;

import com.example.cryptoservice.domain.CryptoRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoRateRepository extends JpaRepository<CryptoRate, Long> {

    Optional<CryptoRate> findByBaseCurrencyAndTargetCurrency(String base, String target);
}
