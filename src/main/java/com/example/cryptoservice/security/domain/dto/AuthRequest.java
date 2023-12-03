package com.example.cryptoservice.security.domain.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}
