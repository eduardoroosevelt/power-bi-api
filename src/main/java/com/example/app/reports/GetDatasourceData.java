package com.example.app.reports;

import com.example.app.config.ConfigAzure;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class GetDatasourceData {

    // Get Datasources In Group Power BI REST API
    public static GetDatasourcesResponse getDatasourcesInGroup(String accessToken, String reportId, String groupId) {

        // Request header
        HttpHeaders reqHeader = new HttpHeaders();
        reqHeader.put("Content-Type", Arrays.asList("application/json"));
        reqHeader.put("Authorization", Arrays.asList("Bearer " + accessToken));

        // HTTP entity object - holds header and body
        HttpEntity<String> reqEntity = new HttpEntity<>(reqHeader);

        // https://docs.microsoft.com/en-us/rest/api/power-bi/datasets/getdatasourcesingroup
        String endPointUrl = ConfigAzure.powerBiApiUrl + "v1.0/myorg/groups/" + groupId + "/reports/" + reportId ;
       // String endPointUrl = ConfigAzure.powerBiApiUrl + "v1.0/myorg/groups/" + groupId + "/datasets/" + datasetId + "/datasources";
      //  String endPointEmbedTokenApi = ConfigAzure.powerBiApiUrl + "v1.0/myorg/GenerateToken";

        // Rest API get datasources's details
        RestTemplate getDatasourceRestTemplate = new RestTemplate();
        ResponseEntity<GetDatasourcesResponse> response = getDatasourceRestTemplate.exchange(endPointUrl, HttpMethod.GET, reqEntity, GetDatasourcesResponse.class);
        // HttpHeaders responseHeader = response.getHeaders();
        return response.getBody();
    }

}
