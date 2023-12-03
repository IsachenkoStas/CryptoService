package com.example.cryptoservice.exception_resolver;

public class SameUserInDatabaseException extends RuntimeException {
    public SameUserInDatabaseException(String message) {
        super(message);
    }
}