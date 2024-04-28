package com.example.desafio_rpe.repository;

import com.example.desafio_rpe.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Boolean existsByCnpj(String cnpj);

    Optional<Store> findByCnpj(String cnpj);
}
