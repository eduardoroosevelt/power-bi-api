package com.example.app.powerBi;

import java.util.List;

public class GenerateEmbedTokenRequest {

    private List<Dataset> datasets;
    private List<Report> reports;


    public static class Dataset {
        private String id;
        public Dataset(String id) { this.id = id; }
        public String getId() { return id; }
    }

    public static class Report {
        private String id;
        public Report(String id) { this.id = id; }
        public String getId() { return id; }
    }

    public GenerateEmbedTokenRequest(String reportId, String datasetId) {
        this.reports = List.of(new Report(reportId));
        this.datasets = List.of(new Dataset(datasetId));
    }

    public List<Dataset> getDatasets() { return datasets; }
    public List<Report> getReports() { return reports; }
}