package com.example.desafio_rpe.dto;

public record StoreDto(
        String cnpj,
        String name,
        String segment,
        String phone,
        String storeType,
        String physicalAddress, // Apenas para LojaFisica
        Integer numberOfEmployees, // Apenas para LojaFisica
        String url, // Apenas para LojaVirtual
        Integer rating // Apenas para LojaVirtual
) {
}

