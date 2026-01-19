package com.example.app.common;

public class ForbiddenException extends ApiException {
    public ForbiddenException(String message) {
        super("FORBIDDEN", message);
    }
}
