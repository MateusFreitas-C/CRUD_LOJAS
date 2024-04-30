package com.example.desafio_rpe.infra;

import com.example.desafio_rpe.exception.StoreAlreadyExistsException;
import com.example.desafio_rpe.exception.StoreNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ConstraintViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class StoreExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(StoreExceptionHandler.class);

    @ExceptionHandler(StoreAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> storeAlreadyExistsExceptionHandler(StoreAlreadyExistsException e){
        ErrorMessage response = new ErrorMessage(HttpStatus.CONFLICT, e.getMessage());
        log.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<ErrorMessage> storeAlreadyNotFoundExceptionHandler(StoreNotFoundException e){
        ErrorMessage response = new ErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        log.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e) {
        List<String> errors = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        ErrorMessage response = new ErrorMessage(HttpStatus.BAD_REQUEST, errors.toString());
        log.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorMessage response = new ErrorMessage(HttpStatus.BAD_REQUEST, errors.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
