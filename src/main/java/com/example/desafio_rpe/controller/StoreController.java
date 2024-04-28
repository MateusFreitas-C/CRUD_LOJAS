package com.example.desafio_rpe.controller;

import com.example.desafio_rpe.dto.StoreDto;
import com.example.desafio_rpe.model.Store;
import com.example.desafio_rpe.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = {"application/json"})
@Tag(name = "store-api")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    private final Logger log = LoggerFactory.getLogger(StoreController.class);

    @Operation(summary = "Realiza o cadastro de lojas", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Loja criada com sucesso")
    })
    @PostMapping("/store")
    public ResponseEntity<Store> createStore(@RequestBody StoreDto newStore) {
        log.debug("Create Store executed");
        Store response = storeService.createStore(newStore);
        log.info("Store saved successfully!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Retorna a lista de todas as lojas cadastradas", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtida com sucesso")
    })
    @GetMapping("/store")
    public ResponseEntity<List<Store>> getAll(){
        List<Store> response = storeService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}