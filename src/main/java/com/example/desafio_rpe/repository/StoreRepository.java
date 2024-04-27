package com.example.desafio_rpe.repository;

import com.example.desafio_rpe.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Boolean existsByCnpj(String cnpj);
}
