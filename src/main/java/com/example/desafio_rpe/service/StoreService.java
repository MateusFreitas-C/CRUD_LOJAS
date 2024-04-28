package com.example.desafio_rpe.service;

import com.example.desafio_rpe.dto.StoreDto;
import com.example.desafio_rpe.exception.StoreAlreadyExistsException;
import com.example.desafio_rpe.model.PhysicalStore;
import com.example.desafio_rpe.model.Store;
import com.example.desafio_rpe.model.StoreType;
import com.example.desafio_rpe.model.VirtualStore;
import com.example.desafio_rpe.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Store createStore(StoreDto newStore) {
        if (existsByCnpj(newStore.cnpj())){
            throw new StoreAlreadyExistsException(newStore.cnpj());
        }
        StoreType type = StoreType.valueOf(newStore.storeType());

        switch (type) {
            case PHYSICAL -> {
                return createPhysicalStore(newStore);
            }
            case VIRTUAL -> {
                return createVirtualStore(newStore);
            }
        }
        return null;
    }

    public Store createPhysicalStore(StoreDto newStore) {
        PhysicalStore store = PhysicalStore.fromDTO(newStore);
        storeRepository.save(store);
        return store;
    }

    public Store createVirtualStore(StoreDto newStore) {
        VirtualStore store = VirtualStore.fromDTO(newStore);
        storeRepository.save(store);
        return store;
    }

    public Boolean existsByCnpj(String cnpj){
        return storeRepository.existsByCnpj(cnpj);
    }

    public List<Store> getAll(){
        return storeRepository.findAll();
    }
}
