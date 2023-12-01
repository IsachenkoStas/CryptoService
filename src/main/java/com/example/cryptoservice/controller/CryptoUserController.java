package com.example.cryptoservice.controller;

import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.domain.dto.UserDto;
import com.example.cryptoservice.service.CryptoUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crypto-users")
public class CryptoUserController {

    private final CryptoUserService cryptoUserService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<CryptoUser>> getAll() {
        List<CryptoUser> resultList = cryptoUserService.getAllUsers();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(modelMapper.map(cryptoUserService.findById(id), UserDto.class), HttpStatus.OK);
    }
}