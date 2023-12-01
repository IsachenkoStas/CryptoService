package com.example.cryptoservice.exception_resolver;

public class NotDepositAccountException extends RuntimeException {
    public NotDepositAccountException(String message) {
        super(message);
    }
}
