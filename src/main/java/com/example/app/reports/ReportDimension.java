package com.example.app.reports;

import com.example.app.common.Enums.ValueType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "report_dimension")
public class ReportDimension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "report_id")
    private PowerBiReport report;

    private String dimensionKey;

    private String dimensionLabel;

    @Enumerated(EnumType.STRING)
    private ValueType valueType;

    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PowerBiReport getReport() {
        return report;
    }

    public void setReport(PowerBiReport report) {
        this.report = report;
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
