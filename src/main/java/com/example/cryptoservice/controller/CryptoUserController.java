package com.example.cryptoservice.controller;

import com.example.cryptoservice.domain.CryptoUser;
import com.example.cryptoservice.domain.dto.UserDto;
import com.example.cryptoservice.service.CryptoUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crypto-users")
public class CryptoUserController {

    private final CryptoUserService cryptoUserService;
    private final ModelMapper modelMapper;

    @GetMapping("/get-all")
    public ResponseEntity<List<CryptoUser>> getAll() {
        return new ResponseEntity<>(cryptoUserService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<UserDto> getUserInfo() {
        return new ResponseEntity<>(modelMapper.map(cryptoUserService.getUser(), UserDto.class), HttpStatus.OK);
    }

    @PutMapping("/edit-user")
    public ResponseEntity<Void> updateUser(@RequestBody UserDto userDto) {
        cryptoUserService.updateUser(userDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long userId) {
        return new ResponseEntity<>(cryptoUserService.deleteUser(userId) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }
}