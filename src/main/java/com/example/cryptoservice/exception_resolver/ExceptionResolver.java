package com.example.cryptoservice.exception_resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public class ExceptionResolver {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpStatus> userNotFoundException(Exception e) {
        log.warn(String.valueOf(e));
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<HttpStatus> accountNotFoundException(Exception e) {
        log.warn(String.valueOf(e));
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
