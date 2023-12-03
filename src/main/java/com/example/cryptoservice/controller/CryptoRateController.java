package com.example.cryptoservice.controller;

import com.example.cryptoservice.domain.dto.RateDto;
import com.example.cryptoservice.service.CryptoRateService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crypto-rates")
public class CryptoRateController {
    private final ModelMapper modelMapper;
    private final CryptoRateService cryptoRateService;

    @GetMapping("/{baseCurr}/to/{targetCurr}")
    public ResponseEntity<BigDecimal> getParticularRate(@PathVariable String baseCurr, @PathVariable String targetCurr) {
        return new ResponseEntity<>(cryptoRateService.getCurrencyRate(baseCurr, targetCurr).getRate(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RateDto>> getAllRates() {
        return new ResponseEntity<>(cryptoRateService.getAllRates().stream()
                .map(rate -> modelMapper.map(rate, RateDto.class)).toList(), HttpStatus.OK);
    }

    @PostMapping("/add-rate")
    public ResponseEntity<Void> addNewRate(@RequestBody RateDto rateDto) {
        cryptoRateService.addNewRate(rateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}