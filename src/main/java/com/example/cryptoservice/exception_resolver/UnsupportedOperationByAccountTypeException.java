package com.example.cryptoservice.exception_resolver;

public class UnsupportedOperationByAccountTypeException extends RuntimeException {
    public UnsupportedOperationByAccountTypeException(String message) {
        super(message);
    }
}
