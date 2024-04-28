package com.example.desafio_rpe.infra;

import com.example.desafio_rpe.exception.StoreAlreadyExistsException;
import com.example.desafio_rpe.exception.StoreNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StoreExceptionHandler {
    @ExceptionHandler(StoreAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> storeAlreadyExistsExceptionHandler(StoreAlreadyExistsException e){
        ErrorMessage response = new ErrorMessage(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<ErrorMessage> storeAlreadyNotFoundExceptionHandler(StoreNotFoundException e){
        ErrorMessage response = new ErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
