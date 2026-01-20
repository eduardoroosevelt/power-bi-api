package com.example.app.reports;

import com.example.app.powerBi.AzureADService;
import com.example.app.powerBi.GenerateEmbedTokenResponse;
import com.example.app.powerBi.GenerateEmbedTokenService;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;

@Component
public class PowerBiClientImpl implements PowerBiClient {

    @Override
    public PowerBiEmbedResult generateEmbed(PowerBiReport report, String principal, String reportKey) {

        try {
            String token = AzureADService.getAccessToken();
            GetDatasourcesResponse dataSource = GetDatasourceData.getDatasourcesInGroup(token,  report.getReportId(),report.getWorkspaceId());

            GenerateEmbedTokenResponse generateEmbedTokenResponse = GenerateEmbedTokenService.generate(token,report.getWorkspaceId(),report.getReportId(),dataSource.getDatasetId());

            String embedUrl =dataSource.getEmbedUrl();

//            return new PowerBiEmbedResult(
//                    embedUrl,
//                    token,
//                    Instant.now().plus(30, ChronoUnit.MINUTES)
//            );
            return new PowerBiEmbedResult(
                    embedUrl,
                    generateEmbedTokenResponse.getToken(),
                    Instant.now().plus(30, ChronoUnit.MINUTES)
            );
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}
