package com.example.app.policies;

import jakarta.validation.constraints.NotBlank;

public class CreateRuleValueRequest {
    @NotBlank
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
