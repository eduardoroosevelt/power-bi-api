package com.example.app.reports;

public interface PowerBiClient {
    PowerBiEmbedResult generateEmbed(PowerBiReport report, String principal, String reportKey);
}
