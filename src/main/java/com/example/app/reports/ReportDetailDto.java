package com.example.app.reports;

import java.util.ArrayList;
import java.util.List;

public class ReportDetailDto {
    private Long id;
    private String nome;
    private String workspaceId;
    private String reportId;
    private String datasetId;
    private boolean ativo;
    private List<ReportDimensionDto> dimensions = new ArrayList<>();
    private List<ReportAccessPolicyDto> policies = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<ReportDimensionDto> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<ReportDimensionDto> dimensions) {
        this.dimensions = dimensions;
    }

    public List<ReportAccessPolicyDto> getPolicies() {
        return policies;
    }

    public void setPolicies(List<ReportAccessPolicyDto> policies) {
        this.policies = policies;
    }
}
