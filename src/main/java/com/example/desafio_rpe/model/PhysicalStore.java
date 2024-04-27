package com.example.desafio_rpe.model;

import com.example.desafio_rpe.dto.StoreDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PHYSICAL")
public class PhysicalStore extends Store{
    private String address;
    private Integer numberOfEmployees;

    public PhysicalStore(){
    }

    public PhysicalStore(String cnpj, String name, String segment, String phone, String address,
                         Integer numberOfEmployees) {
        super(cnpj, name, segment, phone);
        this.address = address;
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(Integer numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    // Método estático para converter de LojaDTO para LojaFisica
    public static PhysicalStore fromDTO(StoreDto dto) {
        return new PhysicalStore(dto.cnpj(), dto.name(), dto.segment(), dto.phone(),
                dto.physicalAddress(), dto.numberOfEmployees());
    }
}
