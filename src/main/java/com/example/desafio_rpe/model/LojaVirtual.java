package com.example.desafio_rpe.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class LojaVirtual extends Loja{
    private String url;
    @Min(0)
    @Max(5)
    private int avaliacao;

    public LojaVirtual(){
    }

    public LojaVirtual(String cnpj, String nome, String segmento, String telefone, String url, int avaliacao) {
        super(cnpj, nome, segmento, telefone);
        this.url = url;
        this.avaliacao = avaliacao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }
}
