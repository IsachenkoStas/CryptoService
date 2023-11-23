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
@Entity(name = "deposits")
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usdt_amount")
    private float usdtAmount;

    @Column(name = "eth_amount")
    private float ethAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Timestamp created;
}