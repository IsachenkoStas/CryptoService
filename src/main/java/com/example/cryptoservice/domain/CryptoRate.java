package com.example.cryptoservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Entity(name = "crypto_rates")
public class CryptoRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base_currency")
    private String baseCurrency;

    @Column(name = "target_currency")
    private String targetCurrency;

    @Column(name = "rate")
    private float rate;
}