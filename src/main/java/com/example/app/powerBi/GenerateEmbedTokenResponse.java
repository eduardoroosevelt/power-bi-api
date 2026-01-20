package com.example.app.powerBi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenerateEmbedTokenResponse {

    @JsonProperty("token")
    private String token;

    @JsonProperty("expiration")
    private String expiration;

    public String getToken() {
        return token;
    }

    public String getExpiration() {
        return expiration;
    }
}