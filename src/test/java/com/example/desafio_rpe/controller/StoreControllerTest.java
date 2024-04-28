package com.example.desafio_rpe.controller;

import com.example.desafio_rpe.dto.StoreDto;
import com.example.desafio_rpe.model.PhysicalStore;
import com.example.desafio_rpe.model.Store;
import com.example.desafio_rpe.service.StoreService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(StoreController.class)
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;

    @Test
    void createStore_ReturnsCreated() throws Exception {
        // Arrange
        StoreDto newStoreDto = new StoreDto("newCnpj", "name", "segment", "phone", "PHYSICAL", "address", 10, null, null);
        Store createdStore = new PhysicalStore();
        when(storeService.createStore(newStoreDto)).thenReturn(createdStore);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cnpj\":\"newCnpj\",\"name\":\"name\",\"segment\":\"segment\",\"phone\":\"phone\",\"storeType\":\"PHYSICAL\",\"address\":\"address\",\"numberOfEmployees\":10}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void getAll_ReturnsListOfStores() throws Exception {
        // Arrange
        List<Store> stores = new ArrayList<>();
        stores.add(new PhysicalStore("cnpj", "name", "segment", "phone", "address", 10));
        when(storeService.getAll()).thenReturn(stores);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/store")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").exists());
    }
}
