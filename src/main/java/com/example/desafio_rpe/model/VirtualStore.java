package com.example.desafio_rpe.model;

import com.example.desafio_rpe.dto.StoreDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@DiscriminatorValue("VIRTUAL")
public class VirtualStore extends Store{
    private String url;
    @Min(0)
    @Max(5)
    private int rating;

    public VirtualStore(){
    }

    public VirtualStore(String cnpj, String name, String segment, String phone, String url, int rating) {
        super(cnpj, name, segment, phone);
        this.url = url;
        this.rating = rating;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // Método estático para converter de LojaDTO para LojaVirtual
    public static VirtualStore fromDTO(StoreDto dto) {
        return new VirtualStore(dto.cnpj(), dto.name(), dto.segment(), dto.phone(),
                dto.url(), dto.rating());
    }
}
