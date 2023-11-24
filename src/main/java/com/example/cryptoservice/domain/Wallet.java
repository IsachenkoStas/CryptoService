package com.example.cryptoservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
@Entity(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_address")
    private String walletAddress;

    @Column(name = "usd_balance")
    private float usdBalance;

    @Column(name = "usdt_balance")
    private float usdtBalance;

    @Column(name = "eth")
    private float eth;

    @Column(name = "btc")
    private float btc;

    @Column(name = "avax")
    private float avax;

    @Column(name = "matic")
    private float matic;

    @Column(name = "sol")
    private float sol;

    @Column(name = "arb")
    private float arb;

    @Column(name = "op")
    private float op;

    @Column(name = "bnb")
    private float bnb;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Timestamp created;
}
