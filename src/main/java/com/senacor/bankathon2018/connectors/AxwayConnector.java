package com.senacor.bankathon2018.connectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senacor.bankathon2018.connectors.model.axway.user.AxwayUserQuery;
import com.senacor.bankathon2018.connectors.model.axway.user.AxwayUserQueryResponse;
import io.vavr.control.Try;
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
    @Value("${axway.key}")
    private String axwayKey;
    private final RestTemplate restTemplate;

    public AxwayConnector(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Try<ResponseEntity<String>> retrieveLogin(String login, String password) {
        UriComponents builder = createUri("/users/login.json");

        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("login", login);
        params.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(params, headers);
        LOG.info("Sending request to obtain session...");
        return Try.of(() -> restTemplate.exchange(builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                String.class));
    }

    public ResponseEntity<AxwayUserQueryResponse> userQuery(AxwayUserQuery axwayUserQuery, String techUsrCookie) throws JsonProcessingException {
        LOG.info("Using Cookie=" + techUsrCookie);
        UriComponents builder = createUri("/users/query.json");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", techUsrCookie);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        ObjectMapper objectMapper = new ObjectMapper();
        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("where", objectMapper.writeValueAsString(axwayUserQuery));

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);

        return restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                AxwayUserQueryResponse.class);
    }

    public Try<ResponseEntity<AxwayUserQueryResponse>> createUser(String login, String pwd, String techUsrCookie) {
        LOG.info("Using Cookie=" + techUsrCookie);
        UriComponents builder = createUri("/users/create.json");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", techUsrCookie);
        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("username", login);
        params.add("password", pwd);
        params.add("password_confirmation", pwd);

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);


        return Try.of(() -> restTemplate.exchange(builder.toUriString(),
                HttpMethod.POST,
                requestEntity,
                AxwayUserQueryResponse.class));
    }

    private UriComponents createUri(String route) {
        return UriComponentsBuilder.fromHttpUrl(axwayBaseUrl + route)
                .queryParam("key", axwayKey)
                .build();
    }
}
