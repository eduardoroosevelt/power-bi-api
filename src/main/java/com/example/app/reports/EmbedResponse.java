package com.example.app.reports;

import java.time.Instant;
import java.util.List;

public class EmbedResponse {
    private String reportInternalId;
    private String embedUrl;
    private String accessToken;
    private Instant expiresAt;
    private String principal;
    private String reportKey;
    private List<EmbedFilter> filters;

    public String getReportInternalId() {
        return reportInternalId;
    }

    public void setReportInternalId(String reportInternalId) {
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

    public List<EmbedFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<EmbedFilter> filters) {
        this.filters = filters;
    }
}
