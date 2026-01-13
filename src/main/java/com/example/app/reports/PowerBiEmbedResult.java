package com.example.app.reports;

import java.time.Instant;

public class PowerBiEmbedResult {
    private final String embedUrl;
    private final String accessToken;
    private final Instant expiresAt;

    public PowerBiEmbedResult(String embedUrl, String accessToken, Instant expiresAt) {
        this.embedUrl = embedUrl;
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}
