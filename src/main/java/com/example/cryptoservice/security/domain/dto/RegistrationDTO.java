package com.example.cryptoservice.security.domain.dto;

import lombok.Data;

@Data
public class RegistrationDTO {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
}