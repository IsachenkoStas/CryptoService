package com.example.cryptoservice.controller;

import com.example.cryptoservice.domain.CryptoRate;
import com.example.cryptoservice.security.filter.JwtAuthenticationFilter;
import com.example.cryptoservice.service.CryptoRateService;
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
@WebMvcTest(value = CryptoRateController.class)
@AutoConfigureMockMvc(addFilters = false)
class CryptoRateControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CryptoRateService cryptoRateService;

    @MockBean
    JwtAuthenticationFilter jaf;

    static List<CryptoRate> rateList = null;

    static CryptoRate cryptoRate = null;

    @BeforeAll
    static void beforeAll() {
        rateList = new ArrayList<>();
        cryptoRate = new CryptoRate();
        cryptoRate.setId(10L);
        cryptoRate.setRate(BigDecimal.valueOf(0.5));
        cryptoRate.setBaseCurrency("BTC");
        cryptoRate.setTargetCurrency("ETH");
        rateList.add(cryptoRate);
    }

    @Test
    void getAllRatesTest() throws Exception {
        Mockito.when(cryptoRateService.getAllRates()).thenReturn(rateList);
        mockMvc.perform(get("/crypto-rates"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", Matchers.hasSize(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].id", Matchers.equalTo(18)));
    }
}