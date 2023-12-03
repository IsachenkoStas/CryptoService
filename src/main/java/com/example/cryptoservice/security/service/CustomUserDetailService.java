package com.example.cryptoservice.security.service;


import com.example.cryptoservice.exception_resolver.UserNotFoundException;
import com.example.cryptoservice.security.domain.SecurityCredentials;
import com.example.cryptoservice.security.repository.SecurityCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    final private SecurityCredentialsRepository securityCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<SecurityCredentials> userFromDB = securityCredentialsRepository.getByLogin(login);
        if (userFromDB.isEmpty()) {
            throw new UserNotFoundException("user with login: " + login + " not fount");
        }
        SecurityCredentials user = userFromDB.get();
        return User
                .withUsername(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
    }
}