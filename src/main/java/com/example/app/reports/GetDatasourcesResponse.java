package com.example.app.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetDatasourcesResponse {
    @JsonProperty("@odata.context")
    private String odataContext;
    private String id;
    private String reportType;
    private String format;
    private String name;
    private String webUrl;
    private String embedUrl;
    @JsonProperty("isFromPbix")
    private Boolean isFromPbix;
    @JsonProperty("isOwnedByMe")
    private Boolean isOwnedByMe;
    private String datasetId;
    private String datasetWorkspaceId;

    public GetDatasourcesResponse() {}

    public String getOdataContext() {
        return odataContext;
    }

    public void setOdataContext(String odataContext) {
        this.odataContext = odataContext;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public Boolean getIsFromPbix() {
        return isFromPbix;
    }

    public void setIsFromPbix(Boolean isFromPbix) {
        this.isFromPbix = isFromPbix;
    }

    public Boolean getIsOwnedByMe() {
        return isOwnedByMe;
    }

    public void setIsOwnedByMe(Boolean isOwnedByMe) {
        this.isOwnedByMe = isOwnedByMe;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    public String getDatasetWorkspaceId() {
        return datasetWorkspaceId;
    }

    public void setDatasetWorkspaceId(String datasetWorkspaceId) {
        this.datasetWorkspaceId = datasetWorkspaceId;
    }
}