package com.example.app.reports;

import com.example.app.common.Enums.ValueType;

public class ReportDimensionDto {
    private Long id;
    private String dimensionKey;
    private String dimensionLabel;
    private ValueType valueType;
    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
