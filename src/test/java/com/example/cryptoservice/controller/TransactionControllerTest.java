package com.example.cryptoservice.controller;

import com.example.cryptoservice.domain.Account;
import com.example.cryptoservice.domain.Transaction;
import com.example.cryptoservice.domain.TransactionType;
import com.example.cryptoservice.security.filter.JwtAuthenticationFilter;
import com.example.cryptoservice.service.TransactionService;
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
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TransactionService transactionService;

    @MockBean
    JwtAuthenticationFilter jaf;

    static List<Transaction> transactionList = null;

    static Transaction transaction = null;

    static Account account = null;

    @BeforeAll
    static void beforeAll() {
        transactionList = new ArrayList<>();
        transaction = new Transaction();
        transaction.setId(10L);
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setAccount(account);
        transactionList.add(transaction);
    }

    @Test
    void getAllTest() throws Exception {
        Mockito.when(transactionService.getAllUserTransactions()).thenReturn(transactionList);
        mockMvc.perform(get("/transactions/all-mine"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", Matchers.hasSize(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].id", Matchers.equalTo(18)));
    }
}