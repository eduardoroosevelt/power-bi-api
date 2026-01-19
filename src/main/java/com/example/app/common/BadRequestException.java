package com.example.app.common;

public class BadRequestException extends ApiException {
    public BadRequestException(String message) {
        super("BAD_REQUEST", message);
    }
}
