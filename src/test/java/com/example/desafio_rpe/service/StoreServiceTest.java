package com.example.desafio_rpe.service;

import com.example.desafio_rpe.dto.StoreDto;
import com.example.desafio_rpe.exception.StoreAlreadyExistsException;
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
}
