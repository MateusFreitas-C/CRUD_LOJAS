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
            @ApiResponse(responseCode = "201", description = "Loja criada com sucesso"),
            @ApiResponse(responseCode = "409", description = "Já existe uma loja com o CNPJ informado")
    })

    @PostMapping("/store")
    public ResponseEntity<Store> createStore(@RequestBody StoreDto newStore) {
        log.info("Create Store executed");
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
        log.info("Get all stores executed");
        List<Store> response = storeService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Retorna a loja com o CNPJ informado", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loja obtida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não foi encontrada uma loja com o CNPJ informado")
    })

    @GetMapping("/store/{cnpj}")
    public ResponseEntity<Store> getOneByCnpj(@PathVariable String cnpj){
        log.info("Get one store by cnpj executed");
        Store response = storeService.getByCNPJ(cnpj);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Atualiza informações da loja com o CNPJ informado", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loja atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não foi encontrada uma loja com o CNPJ informado")
    })
    @PutMapping("/store/{cnpj}")
    public ResponseEntity<Store> updateStore(@PathVariable String cnpj, @RequestBody StoreDto dto){
        log.info("Update store by cnpj executed");
        Store response = storeService.updateStoreTypeAndDetails(dto, cnpj);
        log.info("Update store by cnpj executed successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}