package com.example.desafio_rpe.service;

import com.example.desafio_rpe.dto.StoreDto;
import com.example.desafio_rpe.exception.StoreAlreadyExistsException;
import com.example.desafio_rpe.exception.StoreNotFoundException;
import com.example.desafio_rpe.model.PhysicalStore;
import com.example.desafio_rpe.model.Store;
import com.example.desafio_rpe.model.VirtualStore;
import com.example.desafio_rpe.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    @Test
    void createStore_PhysicalStore_CallsCreatePhysicalStore() {
        // Arrange
        StoreDto newStoreDto = new StoreDto("newCnpj", "name", "segment", "phone", "PHYSICAL", "address", 10, null, null);
        when(storeRepository.existsByCnpj("newCnpj")).thenReturn(false);

        // Act
        storeService.createStore(newStoreDto);

        // Verify
        verify(storeRepository).existsByCnpj("newCnpj");
        verify(storeRepository).save(any(PhysicalStore.class));
    }

    @Test
    void createStore_VirtualStore_CallsCreateVirtualStore() {
        // Arrange
        StoreDto newStoreDto = new StoreDto("newCnpj", "name", "segment", "phone", "VIRTUAL", null, 10, "url", 5);
        when(storeRepository.existsByCnpj("newCnpj")).thenReturn(false);

        // Act
        storeService.createStore(newStoreDto);

        // Verify
        verify(storeRepository).existsByCnpj("newCnpj");
        verify(storeRepository).save(any(VirtualStore.class));
    }

    @Test
    void createStore_StoreAlreadyExists_ThrowsException() {
        // Arrange
        StoreDto existingStoreDto = new StoreDto("existingCnpj", "name", "segment", "phone", "PHYSICAL", "address", 10, null, null);
        when(storeRepository.existsByCnpj("existingCnpj")).thenReturn(true);

        // Act & Assert
        assertThrows(StoreAlreadyExistsException.class, () -> storeService.createStore(existingStoreDto));

        // Verify
        verify(storeRepository).existsByCnpj("existingCnpj");
        verify(storeRepository, never()).save(any());
    }

    @Test
    void existsByCnpj_CnpjExists_ReturnsTrue() {
        // Arrange
        String existingCnpj = "existingCnpj";
        when(storeRepository.existsByCnpj(existingCnpj)).thenReturn(true);

        // Act
        boolean result = storeService.existsByCnpj(existingCnpj);

        // Assert
        assertTrue(result);

        // Verify
        verify(storeRepository).existsByCnpj(existingCnpj);
    }

    @Test
    void existsByCnpj_CnpjDoesNotExist_ReturnsFalse() {
        // Arrange
        String nonExistingCnpj = "nonExistingCnpj";
        when(storeRepository.existsByCnpj(nonExistingCnpj)).thenReturn(false);

        // Act
        boolean result = storeService.existsByCnpj(nonExistingCnpj);

        // Assert
        assertFalse(result);

        // Verify
        verify(storeRepository).existsByCnpj(nonExistingCnpj);
    }

    @Test
    void getAll_ReturnsListOfStores() {
        // Arrange
        List<Store> expected = new ArrayList<>();
        expected.add(new PhysicalStore());
        expected.add(new VirtualStore());
        when(storeRepository.findAll()).thenReturn(expected);

        // Act
        List<Store> result = storeService.getAll();

        // Assert
        assertEquals(expected, result);

        // Verify
        verify(storeRepository).findAll();
    }

    @Test
    void getByCNPJ_ThrowsException() {
        // Arrange
        String cnpj = "123456789"; // CNPJ inexistente

        // Act & Assert
        assertThrows(StoreNotFoundException.class, () -> storeService.getByCNPJ(cnpj));

        verify(storeRepository).findByCnpj(any());
    }

    @Test
    void getByCNPJ_ReturnsStore() {
        // Arrange
        String cnpj = "123456789"; // CNPJ existente
        Store expectedStore = new PhysicalStore("123456789", "Store Name", "Segment", "1234567890", "Address", 5);
        when(storeRepository.findByCnpj(cnpj)).thenReturn(Optional.of(expectedStore));

        // Act
        Store actualStore = storeService.getByCNPJ(cnpj);

        // Assert
        assertEquals(expectedStore, actualStore);
    }

    @Test
    void updateVirtualStore_ReturnsUpdatedVirtualStore() {
        // Arrange
        String cnpj = "virtualCnpj";
        VirtualStore virtualStore = new VirtualStore();
        virtualStore.setCnpj(cnpj);
        StoreDto dto = new StoreDto(cnpj, "Virtual Store", "Segment", "123456789", "VIRTUAL", null, null, "http://example.com", 5);
        when(storeRepository.save(any(VirtualStore.class))).thenReturn(virtualStore);

        // Act
        Store updatedStore = storeService.updateVirtualStore(virtualStore, dto);

        // Assert
        assertEquals(dto.name(), updatedStore.getName());
        assertEquals(dto.segment(), updatedStore.getSegment());
        assertEquals(dto.phone(), updatedStore.getPhone());
        assertEquals(dto.rating(), ((VirtualStore) updatedStore).getRating());
        assertEquals(dto.url(), ((VirtualStore) updatedStore).getUrl());
        verify(storeRepository).save(any(VirtualStore.class));
    }

    @Test
    void uptadePhysicalStore_ReturnsUpdatedPhysicalStore() {
        // Arrange
        String cnpj = "physicalCnpj";
        PhysicalStore physicalStore = new PhysicalStore();
        physicalStore.setCnpj(cnpj);
        StoreDto dto = new StoreDto(cnpj, "Physical Store", "Segment", "123456789", "PHYSICAL", "Address", 10, null, null);
        when(storeRepository.save(any(PhysicalStore.class))).thenReturn(physicalStore);

        // Act
        Store updatedStore = storeService.uptadePhysicalStore(physicalStore, dto);

        // Assert
        assertEquals(dto.name(), updatedStore.getName());
        assertEquals(dto.segment(), updatedStore.getSegment());
        assertEquals(dto.phone(), updatedStore.getPhone());
        assertEquals(dto.physicalAddress(), ((PhysicalStore) updatedStore).getAddress());
        assertEquals(dto.numberOfEmployees(), ((PhysicalStore) updatedStore).getNumberOfEmployees());
        verify(storeRepository).save(any(PhysicalStore.class));
    }

    @Test
    void updateStoreTypeAndDetails_VirtualStore_ReturnsUpdatedVirtualStore() {
        // Arrange
        String cnpj = "virtualCnpj";
        VirtualStore virtualStore = new VirtualStore();
        virtualStore.setCnpj(cnpj);
        StoreDto dto = new StoreDto(cnpj, "Virtual Store", "Segment", "123456789", "VIRTUAL", null, null, "http://example.com", 5);
        when(storeRepository.findByCnpj(cnpj)).thenReturn(Optional.of(virtualStore));
        when(storeRepository.save(any(VirtualStore.class))).thenReturn(virtualStore);

        // Act
        Store updatedStore = storeService.updateStoreTypeAndDetails(dto, cnpj);

        // Assert
        assertEquals(dto.name(), updatedStore.getName());
        assertEquals(dto.segment(), updatedStore.getSegment());
        assertEquals(dto.phone(), updatedStore.getPhone());
        assertEquals(dto.rating(), ((VirtualStore) updatedStore).getRating());
        assertEquals(dto.url(), ((VirtualStore) updatedStore).getUrl());
        verify(storeRepository).save(any(VirtualStore.class));
    }

    @Test
    void updateStoreTypeAndDetails_PhysicalStore_ReturnsUpdatedPhysicalStore() {
        // Arrange
        String cnpj = "physicalCnpj";
        PhysicalStore physicalStore = new PhysicalStore();
        physicalStore.setCnpj(cnpj);
        StoreDto dto = new StoreDto(cnpj, "Physical Store", "Segment", "123456789", "PHYSICAL", "Address", 10, null, null);
        when(storeRepository.findByCnpj(cnpj)).thenReturn(java.util.Optional.of(physicalStore));
        when(storeRepository.save(any(PhysicalStore.class))).thenReturn(physicalStore);

        // Act
        Store updatedStore = storeService.updateStoreTypeAndDetails(dto, cnpj);

        // Assert
        assertEquals(dto.name(), updatedStore.getName());
        assertEquals(dto.segment(), updatedStore.getSegment());
        assertEquals(dto.phone(), updatedStore.getPhone());
        assertEquals(dto.physicalAddress(), ((PhysicalStore) updatedStore).getAddress());
        assertEquals(dto.numberOfEmployees(), ((PhysicalStore) updatedStore).getNumberOfEmployees());
        verify(storeRepository).save(any(PhysicalStore.class));
    }

    @Test
    void updateStoreTypeAndDetails_StoreNotFound_ThrowsException() {
        // Arrange
        String cnpj = "nonexistentCnpj";
        StoreDto dto = new StoreDto(cnpj, "Nonexistent Store", "Segment", "123456789", "PHYSICAL", "Address", 10, null, null);
        when(storeRepository.findByCnpj(cnpj)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(StoreNotFoundException.class, () -> storeService.updateStoreTypeAndDetails(dto, cnpj));
    }

    @Test
    void deleteStore_CallsRepositoryDelete() {
        // Arrange
        String cnpj = "testCnpj";
        Store store = new PhysicalStore();
        when(storeRepository.findByCnpj(cnpj)).thenReturn(java.util.Optional.of(store));

        // Act
        storeService.deleteStore(cnpj);

        // Assert
        verify(storeRepository).delete(store);
    }
}
