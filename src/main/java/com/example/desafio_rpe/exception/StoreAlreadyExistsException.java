package com.example.desafio_rpe.exception;

public class StoreAlreadyExistsException extends RuntimeException{
    public StoreAlreadyExistsException(String cnpj){
        super("A store with the CNPJ " + cnpj +" already exists. Please check the information and try again.\"");
    }
}
