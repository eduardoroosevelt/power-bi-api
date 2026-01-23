package com.example.app.policies;

import com.example.app.common.Enums.RuleOperator;
import com.example.app.common.Enums.ValuesMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CreateRuleRequest {
    @NotBlank
    private String dimensionKey;
    private RuleOperator operator;
    @NotNull
    private ValuesMode valuesMode;
    private String userAttribute;
    private boolean allowAll;
    @NotNull
    private Boolean active;
    private List<String> values;

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

    public boolean isAllowAll() {
        return allowAll;
    }

    public void setAllowAll(boolean allowAll) {
        this.allowAll = allowAll;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
