package com.example.cryptoservice.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "currency_code", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyCode currencyCode;

    @Column(name = "account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "last_interest_application")
    private LocalDateTime lastInterestApplication;

    @Column(name = "interest_compounding_period")
    @Enumerated(EnumType.STRING)
    private InterestCompoundingPeriod interestCompoundingPeriod;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private LocalDateTime created;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private CryptoUser user;
}
