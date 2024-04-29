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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

    @Test
    void getByCNPJ_ReturnsStore() throws Exception {
        // Arrange
        String cnpj = "123456789"; // CNPJ existente
        Store expectedStore = new PhysicalStore(cnpj, "Store Name", "Segment", "1234567890", "Address", 5);
        when(storeService.getByCNPJ(cnpj)).thenReturn(expectedStore);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/store/{cnpj}", cnpj)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.cnpj").value(cnpj))
                .andExpect(jsonPath("$.name").value(expectedStore.getName()))
                .andExpect(jsonPath("$.segment").value(expectedStore.getSegment()));
    }

    @Test
    void updateStoreTypeAndDetails_ReturnsUpdatedStore() throws Exception {
        // Arrange
        String cnpj = "physicalCnpj";
        PhysicalStore physicalStore = new PhysicalStore();
        physicalStore.setCnpj(cnpj);
        StoreDto dto = new StoreDto(cnpj, "Physical Store", "Segment", "123456789", "PHYSICAL", "Address", 10, null, null);
        when(storeService.getByCNPJ(cnpj)).thenReturn(physicalStore);
        when(storeService.uptadePhysicalStore(any(PhysicalStore.class), eq(dto))).thenReturn(physicalStore);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/store/" + cnpj)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cnpj\":\"" + cnpj + "\",\"name\":\"Physical Store\",\"segment\":\"Segment\",\"phone\":\"123456789\",\"storeType\":\"PHYSICAL\",\"address\":\"Address\",\"numberOfEmployees\":10}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
