package com.example.app.reports;

import java.time.Instant;

public class EmbedResponse {
    private Long reportInternalId;
    private String embedUrl;
    private String accessToken;
    private Instant expiresAt;
    private String principal;
    private String reportKey;

    public Long getReportInternalId() {
        return reportInternalId;
    }

    public void setReportInternalId(Long reportInternalId) {
        this.reportInternalId = reportInternalId;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getReportKey() {
        return reportKey;
    }

    public void setReportKey(String reportKey) {
        this.reportKey = reportKey;
    }
}
