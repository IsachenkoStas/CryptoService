package com.example.cryptoservice.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDto {

    @NotNull
    private Long accIdFrom;

    @NotNull
    private Long accIdTo;

    @NotNull
    @Min(value = 0)
    private BigDecimal amount;
}
