package com.example.app.config.dto;

import jakarta.validation.constraints.NotBlank;

public class CreatePermissaoRequest {
    @NotBlank
    private String code;
    private String descricao;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
