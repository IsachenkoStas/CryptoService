package com.example.cryptoservice.validation;

import com.example.cryptoservice.repository.CryptoUserRepository;
import com.example.cryptoservice.service.CryptoUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {

    private final CryptoUserService userService;

    @Override
    public void initialize(UniqueLogin constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        boolean loginExists = userService.countByLogin(value) > 0;

        return !loginExists;
    }
}
