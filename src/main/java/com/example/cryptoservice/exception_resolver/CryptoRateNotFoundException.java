package com.example.cryptoservice.exception_resolver;

public class CryptoRateNotFoundException extends RuntimeException {
    public CryptoRateNotFoundException(String message) {
        super(message);
    }
}