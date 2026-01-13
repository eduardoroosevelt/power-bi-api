package com.example.app.reports;

import com.example.app.common.Enums.ValueType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateDimensionRequest {
    @NotBlank
    private String dimensionKey;
    @NotBlank
    private String dimensionLabel;
    @NotNull
    private ValueType valueType;
    @NotNull
    private Boolean active;

    public String getDimensionKey() {
        return dimensionKey;
    }

    public void setDimensionKey(String dimensionKey) {
        this.dimensionKey = dimensionKey;
    }

    public String getDimensionLabel() {
        return dimensionLabel;
    }

    public void setDimensionLabel(String dimensionLabel) {
        this.dimensionLabel = dimensionLabel;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
