package com.example.desafio_rpe.model;

public class LojaFisica extends Loja{
    private String endereco;
    private Integer numFuncionarios;

    public LojaFisica(){
    }

    public LojaFisica(String cnpj, String nome, String segmento, String telefone, String endereco,
                      Integer numFuncionarios) {
        super(cnpj, nome, segmento, telefone);
        this.endereco = endereco;
        this.numFuncionarios = numFuncionarios;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getNumFuncionarios() {
        return numFuncionarios;
    }

    public void setNumFuncionarios(Integer numFuncionarios) {
        this.numFuncionarios = numFuncionarios;
    }
}
