package com.example.cryptoservice.service;

import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.repository.CryptoUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CryptoUserServiceImplTest {

    @InjectMocks
    CryptoUserServiceImpl cryptoUserService;

    @Mock
    CryptoUserRepository cryptoUserRepository;

    static List<CryptoUser> userList = null;

    static CryptoUser cryptoUser = null;

    @BeforeAll
    static void beforeAll() {
        userList = new ArrayList<>();
        cryptoUser = new CryptoUser();
        cryptoUser.setId(10L);
        cryptoUser.setFirstName("Stas");
        cryptoUser.setLastName("Stas");
        userList.add(cryptoUser);
    }

    @Test
    void getAll() {
        Mockito.when(cryptoUserRepository.findAll()).thenReturn(userList);

        List<CryptoUser> resultList = cryptoUserService.getAllUsers();
        Mockito.verify(cryptoUserRepository, Mockito.times(1)).findAll();
        Assertions.assertNotNull(resultList);
    }
}