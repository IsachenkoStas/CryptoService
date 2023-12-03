package com.example.cryptoservice.controller;

import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.security.filter.JwtAuthenticationFilter;
import com.example.cryptoservice.service.CryptoUserService;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CryptoUserController.class)
@AutoConfigureMockMvc(addFilters = false)
class CryptoUserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CryptoUserService cryptoUserService;

    @MockBean
    JwtAuthenticationFilter jaf;

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
    void getAllTest() throws Exception {
        Mockito.when(cryptoUserService.getAllUsers()).thenReturn(userList);
        mockMvc.perform(get("/users/get-all"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", Matchers.hasSize(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].id", Matchers.equalTo(10)));
    }


}