package com.example.app.reports;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class FakePowerBiClient implements PowerBiClient {
    @Override
    public PowerBiEmbedResult generateEmbed(PowerBiReport report, String principal, String reportKey) {
        String token = "mock-token-" + UUID.randomUUID();
        String embedUrl = "https://powerbi.mock/embed/" + report.getReportId();
        return new PowerBiEmbedResult(embedUrl, token, Instant.now().plus(30, ChronoUnit.MINUTES));
    }
}
