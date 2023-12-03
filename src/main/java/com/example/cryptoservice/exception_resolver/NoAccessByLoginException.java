package com.example.cryptoservice.exception_resolver;

public class NoAccessByLoginException extends RuntimeException {
    String login;

    public NoAccessByLoginException(String username) {
        this.login = username;
    }

    @Override
    public String toString() {
        return "NoAccessByLoginException{" +
                "login='" + login + '\'' +
                '}';
    }
}