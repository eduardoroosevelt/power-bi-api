package com.example.app.config.dto;

import jakarta.validation.constraints.NotNull;

public class GrupoPermissaoRequest {
    @NotNull
    private Long permissaoId;

    public Long getPermissaoId() {
        return permissaoId;
    }

    public void setPermissaoId(Long permissaoId) {
        this.permissaoId = permissaoId;
    }
}
