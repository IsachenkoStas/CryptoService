package com.example.cryptoservice.exception_resolver;

public class NotEqualCurrencyException extends RuntimeException {
    public NotEqualCurrencyException(String message) {
        super(message);
    }
}