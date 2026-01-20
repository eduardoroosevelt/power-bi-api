package com.example.app.powerBi;

import com.example.app.config.ConfigAzure;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class GenerateEmbedTokenService {

    public static GenerateEmbedTokenResponse generate(
            String accessToken,
            String groupId,
            String reportId,
            String datasetId
    ) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(MediaType.parseMediaTypes("application/json"));

        GenerateEmbedTokenRequest body =
                new GenerateEmbedTokenRequest(reportId, datasetId);

        HttpEntity<GenerateEmbedTokenRequest> request =
                new HttpEntity<>(body, headers);

//        String url = ConfigAzure.powerBiApiUrl
//                + "v1.0/myorg/groups/"
//                + groupId
//                + "/reports/"
//                + reportId
//                + "/GenerateToken";

        String url = ConfigAzure.powerBiApiUrl + "v1.0/myorg/GenerateToken";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<GenerateEmbedTokenResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        request,
                        GenerateEmbedTokenResponse.class
                );

        return response.getBody();
    }


}
