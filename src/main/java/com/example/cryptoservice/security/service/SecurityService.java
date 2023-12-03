package com.example.cryptoservice.security.service;

import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.domain.Role;
import com.example.cryptoservice.exception_resolver.SameUserInDatabaseException;
import com.example.cryptoservice.repository.CryptoUserRepository;
import com.example.cryptoservice.security.domain.SecurityCredentials;
import com.example.cryptoservice.security.domain.dto.AuthRequest;
import com.example.cryptoservice.security.domain.dto.RegistrationDTO;
import com.example.cryptoservice.security.repository.SecurityCredentialsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SecurityService {
    private final SecurityCredentialsRepository securityCredentialsRepository;
    private final CryptoUserRepository cryptoUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public Optional<String> generateToken(AuthRequest authRequest) {
        Optional<SecurityCredentials> personCredentials = securityCredentialsRepository.getByLogin(authRequest.getLogin());
        if (personCredentials.isPresent() &&
                passwordEncoder.matches(authRequest.getPassword(), personCredentials.get().getPassword())) {
            return Optional.of(jwtUtils.generateJwtToken(authRequest.getLogin()));
        }
        return Optional.empty();
    }

    @Transactional
    public void registration(RegistrationDTO registrationDTO) {
        Optional<SecurityCredentials> result = securityCredentialsRepository.getByLogin(registrationDTO.getLogin());
        if (result.isPresent()) {
            throw new SameUserInDatabaseException("User with this login already exists");
        }
        CryptoUser user = new CryptoUser();
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        CryptoUser userInfoResult = cryptoUserRepository.save(user);

        SecurityCredentials securityCredentials = new SecurityCredentials();
        securityCredentials.setLogin(registrationDTO.getLogin());
        securityCredentials.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        securityCredentials.setRole(Role.USER);
        securityCredentials.setUserId(userInfoResult.getId());
        securityCredentialsRepository.save(securityCredentials);
    }

    public boolean checkAccessById(Long id) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        String userRole = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get());
        Long userId = securityCredentialsRepository.findUserIdByLogin(userLogin);
        return (userId.equals(id) || userRole.equals("ROLE_ADMIN"));
    }
}