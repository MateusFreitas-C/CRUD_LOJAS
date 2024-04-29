package com.example.desafio_rpe.infra.security.exceptionHandler;

import com.example.desafio_rpe.infra.ErrorMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ControllerAdvice
public class SecurityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(SecurityExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> illegalArgumentExceptionHandler(IllegalArgumentException e){
        ErrorMessage response = new ErrorMessage(HttpStatus.FORBIDDEN, e.getMessage());
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> usernameNotFoundExceptionHandler(UsernameNotFoundException e){
        ErrorMessage response = new ErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
