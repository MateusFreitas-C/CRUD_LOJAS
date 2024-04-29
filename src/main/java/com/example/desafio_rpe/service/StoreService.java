package com.example.desafio_rpe.service;

import com.example.desafio_rpe.dto.StoreDto;
import com.example.desafio_rpe.exception.StoreAlreadyExistsException;
import com.example.desafio_rpe.exception.StoreNotFoundException;
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
        if (existsByCnpj(newStore.cnpj())) {
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

    public Boolean existsByCnpj(String cnpj) {
        return storeRepository.existsByCnpj(cnpj);
    }

    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    public Store getByCNPJ(String cnpj) {
        return storeRepository.findByCnpj(cnpj).orElseThrow(() -> new StoreNotFoundException(cnpj));
    }

    public Store updateStoreTypeAndDetails(StoreDto dto, String cnpj) {
        Store actualStore = getByCNPJ(cnpj);
        StoreType newType = StoreType.valueOf(dto.storeType());

        switch (newType) {
            case PHYSICAL -> {
                return uptadePhysicalStore(actualStore, dto);
            }
            case VIRTUAL -> {
                return updateVirtualStore(actualStore, dto);
            }
        }
        return null;
    }

    Store updateVirtualStore(Store actualStore, StoreDto dto) {
        VirtualStore virtualStore = (VirtualStore) actualStore;
        virtualStore.setName(dto.name());
        virtualStore.setSegment(dto.segment());
        virtualStore.setPhone(dto.phone());
        virtualStore.setRating(dto.rating());
        virtualStore.setUrl(dto.url());

        return storeRepository.save(virtualStore);
    }

    public Store uptadePhysicalStore(Store actualStore, StoreDto dto) {
        PhysicalStore physicalStore = (PhysicalStore) actualStore;
        physicalStore.setName(dto.name());
        physicalStore.setSegment(dto.segment());
        physicalStore.setPhone(dto.phone());
        physicalStore.setAddress(dto.physicalAddress());
        physicalStore.setNumberOfEmployees(dto.numberOfEmployees());

        return storeRepository.save(physicalStore);
    }
}
