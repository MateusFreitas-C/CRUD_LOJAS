package com.example.desafio_rpe.controller;

import com.example.desafio_rpe.dto.StoreDto;
import com.example.desafio_rpe.model.Store;
import com.example.desafio_rpe.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    private final Logger log = LoggerFactory.getLogger(StoreController.class);

    @PostMapping("/store")
    public ResponseEntity<Store> createStore(@RequestBody StoreDto newStore) {
        log.debug("Create Store executed");
        Store response = storeService.createStore(newStore);
        log.info("Store saved successfully!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/store")
    public ResponseEntity<List<Store>> getAll(){
        List<Store> response = storeService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}