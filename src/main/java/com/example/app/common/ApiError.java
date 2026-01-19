package com.example.app.common;

import java.util.List;

public class ApiError {
    private String code;
    private String message;
    private List<String> details;

    public ApiError(String code, String message, List<String> details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getDetails() {
        return details;
    }
}
