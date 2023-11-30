package com.example.cryptoservice.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RateDto {

    private String baseCurrency;

    private String targetCurrency;

    private BigDecimal rate;
}
