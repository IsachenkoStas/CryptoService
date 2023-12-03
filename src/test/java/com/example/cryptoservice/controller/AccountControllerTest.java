package com.example.cryptoservice.controller;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.AccountType;
import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.domain.CurrencyCode;
import com.example.cryptoservice.security.filter.JwtAuthenticationFilter;
import com.example.cryptoservice.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AccountService accountService;

    @MockBean
    JwtAuthenticationFilter jaf;

    static List<Account> accountList = null;

    static Account account = null;

    static CryptoUser cryptoUser = null;

    @BeforeAll
    static void beforeAll() {
        accountList = new ArrayList<>();
        account = new Account();
        account.setId(10L);
        account.setBalance(BigDecimal.valueOf(100));
        account.setAccountType(AccountType.SAVING);
        account.setCurrencyCode(CurrencyCode.ETH);
        account.setCreated(LocalDateTime.now());
        account.setUser(cryptoUser);
        accountList.add(account);
    }

    @Test
    void getAllAccTest() throws Exception {
        Mockito.when(accountService.getAllUserAccounts()).thenReturn(accountList);
        mockMvc.perform(get("/accounts/all-mine"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", Matchers.hasSize(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].id", Matchers.equalTo(18)));
    }
}