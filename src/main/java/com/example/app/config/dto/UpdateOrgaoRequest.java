package com.example.app.config.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateOrgaoRequest {
    @NotBlank
    private String nome;
    @NotBlank
    private String codigo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
