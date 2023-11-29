package com.example.cryptoservice.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositDto {

    @NotNull
    @Min(value = 0, message = "Transfer amount can not be less than zero")
    private BigDecimal amount;

    @NotNull
    private Long accId;
}