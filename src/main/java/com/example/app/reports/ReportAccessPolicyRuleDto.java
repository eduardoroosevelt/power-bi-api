package com.example.app.reports;

import com.example.app.common.Enums.RuleOperator;
import com.example.app.common.Enums.ValuesMode;
import java.util.ArrayList;
import java.util.List;

public class ReportAccessPolicyRuleDto {
    private Long id;
    private String dimensionKey;
    private RuleOperator operator;
    private ValuesMode valuesMode;
    private String userAttribute;
    private boolean active;
    private List<String> values = new ArrayList<>();

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

    public RuleOperator getOperator() {
        return operator;
    }

    public void setOperator(RuleOperator operator) {
        this.operator = operator;
    }

    public ValuesMode getValuesMode() {
        return valuesMode;
    }

    public void setValuesMode(ValuesMode valuesMode) {
        this.valuesMode = valuesMode;
    }

    public String getUserAttribute() {
        return userAttribute;
    }

    public void setUserAttribute(String userAttribute) {
        this.userAttribute = userAttribute;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
