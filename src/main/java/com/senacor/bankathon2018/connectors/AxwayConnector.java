package com.senacor.bankathon2018.connectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senacor.bankathon2018.connectors.model.axway.AxwayUserQuery;
import com.senacor.bankathon2018.connectors.model.axway.AxwayUserQueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AxwayConnector {

    private final static Logger LOG = LoggerFactory.getLogger(AxwayConnector.class);

    @Value("${axway.baseUrl}")
    private String axwayBaseUrl;
    private final RestTemplate restTemplate;

    public AxwayConnector(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> retrieveLogin(String login, String password) {
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(axwayBaseUrl + "/users/login.json")
                .queryParam("key", "WdR23JkBo8jrElnHunaOcgP7Qu8ZCTBr")
                .build();

        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("login", login);
        params.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(params, headers);
        LOG.info("Sending request to obtain session...");
        return restTemplate.exchange(builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                String.class);
    }

    public ResponseEntity<AxwayUserQueryResponse> userQuery(AxwayUserQuery axwayUserQuery, String cookie) throws JsonProcessingException {
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(axwayBaseUrl + "/users/query.json")
                .queryParam("key", "WdR23JkBo8jrElnHunaOcgP7Qu8ZCTBr")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie);

        ObjectMapper objectMapper = new ObjectMapper();
        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("where", objectMapper.writeValueAsString(axwayUserQuery));

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);

        return restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                AxwayUserQueryResponse.class);
    }
}
