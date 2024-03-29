package com.senacor.bankathon2018.connectors;

import com.senacor.bankathon2018.connectors.model.figo.CreateNotificationEntity;
import com.senacor.bankathon2018.connectors.model.figo.GetNotificationEntity;
import com.senacor.bankathon2018.connectors.model.figo.GetNotificationWrapper;
import com.senacor.bankathon2018.connectors.model.figo.TransactionsEntity;
import com.senacor.bankathon2018.connectors.model.figo.UserId;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.Credentials;
import io.vavr.control.Try;
import me.figo.FigoConnection;
import me.figo.internal.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class FigoConnector {

    private final static Logger LOG = LoggerFactory.getLogger(FigoConnector.class);


    @Value("${figo.baseUrl}")
    private String figoBaseUrl;
  @Value("${server.url}")
  private String serverUrl;

    private final FigoConnection figoConnection;
    private final RestTemplate restTemplate;


    public FigoConnector(FigoConnection figoConnection, RestTemplate restTemplate) {
        this.figoConnection = figoConnection;
        this.restTemplate = restTemplate;
    }

    public Try<TokenResponse> figoLogin(Credentials credentials) {
        return Try.of(() -> figoConnection.credentialLogin(credentials.getUsername(), credentials.getPassword()));
    }

    public TransactionsEntity getTransactions(String accessToken, String lastTransactionID,
        boolean includePending) {
        LOG.info("accessToken=" + accessToken);
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(figoBaseUrl + "/rest/transactions")
                .queryParam("since", lastTransactionID)
                .queryParam("include_pending", includePending)
                .build();

        LOG.info("URL=" + builder.toString());
        HttpEntity<Void> request = createGetHttpEntity(accessToken);

        return restTemplate
            .exchange(builder.toUriString(), HttpMethod.GET, request, TransactionsEntity.class)
            .getBody();
    }

  public Try<ResponseEntity<UserId>> getUserId(String accessToken) {
        LOG.info("accessToken=" + accessToken);

    return Try.of(() -> restTemplate.exchange(figoBaseUrl + "/rest/user",
                HttpMethod.GET,
                createGetHttpEntity(accessToken),
        UserId.class));
  }

  public Try<ResponseEntity<GetNotificationEntity>> createSubscription(String username,
      String accessToken) {
    LOG.info("accessToken=" + accessToken);
    CreateNotificationEntity createNotificationEntity = new CreateNotificationEntity();
    createNotificationEntity.setNotify_uri(serverUrl + "/figo/notification");
    createNotificationEntity.setObserve_key("/rest/transactions");
    createNotificationEntity.setState(username);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + accessToken);
    HttpEntity<CreateNotificationEntity> httpEntity = new HttpEntity<>(createNotificationEntity,
        headers);

    return Try.of(() -> restTemplate.exchange(figoBaseUrl + "/rest/notifications",
        HttpMethod.POST,
        httpEntity,
        GetNotificationEntity.class));
  }

  public Try<ResponseEntity<GetNotificationWrapper>> getNotifications(String accessToken) {
    return Try.of(() -> restTemplate.exchange(figoBaseUrl + "/rest/notifications",
        HttpMethod.GET,
        createGetHttpEntity(accessToken),
        GetNotificationWrapper.class));
    }

    private HttpEntity<Void> createGetHttpEntity(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);
        return new HttpEntity<>(headers);
    }

}
