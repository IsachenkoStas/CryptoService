package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.domain.Role;
import com.example.cryptoservice.domain.dto.UserDto;
import com.example.cryptoservice.exception_resolver.NoAccessByLoginException;
import com.example.cryptoservice.exception_resolver.UserNotFoundException;
import com.example.cryptoservice.repository.CryptoUserRepository;
import com.example.cryptoservice.security.domain.SecurityCredentials;
import com.example.cryptoservice.security.repository.SecurityCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CryptoUserServiceImpl implements CryptoUserService {

    private final CryptoUserRepository cryptoUserRepository;
    private final SecurityCredentialsRepository securityCredentialsRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<CryptoUser> getAllUsers() {
        return cryptoUserRepository.findAll();
    }

    @Override
    public CryptoUser findById(Long id) {
        return cryptoUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public CryptoUser getUser() {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = securityCredentialsRepository.findUserIdByLogin(userLogin);
        return cryptoUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with login " + userLogin + " not found"));
    }

    @Override
    public void updateUser(UserDto userDto) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = securityCredentialsRepository.findUserIdByLogin(userLogin);
        CryptoUser user = cryptoUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with login " + userLogin + " not found"));
        modelMapper.map(userDto, user);
        cryptoUserRepository.save(user);
    }

    @Override
    public boolean deleteUser(Long userId) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        SecurityCredentials userCredentials = securityCredentialsRepository.getByLogin(userLogin)
                .orElseThrow(() -> new UserNotFoundException("User with login " + userLogin + " not found"));
        if (!Objects.equals(userCredentials.getRole(), Role.ADMIN)) {
            throw new NoAccessByLoginException(userLogin);
        }
        cryptoUserRepository.deleteById(userId);
        return true;
    }
}