package com.example.desafio_rpe.exception;

public class StoreNotFoundException extends RuntimeException{
    public StoreNotFoundException(String cnpj){
        super("A store with the CNPJ " + cnpj +" was not found. Please check the information and try again.");
    }
}
