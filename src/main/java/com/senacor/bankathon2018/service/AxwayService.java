package com.senacor.bankathon2018.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senacor.bankathon2018.connectors.AxwayConnector;
import com.senacor.bankathon2018.connectors.FigoConnector;
import com.senacor.bankathon2018.connectors.model.axway.AxwayUserQuery;
import com.senacor.bankathon2018.connectors.model.axway.AxwayUserQueryResponse;
import com.senacor.bankathon2018.service.model.AxwaySession;
import com.senacor.bankathon2018.service.repository.AxwaySessionRepository;
import com.senacor.bankathon2018.webendpoint.model.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AxwayService {

    private final static Logger LOG = LoggerFactory.getLogger(AxwayService.class);

    @Value("${axway.techusr.login}")
    private String axwayTechUsrLogin;
    @Value("${axway.techusr.pwd}")
    private String axwayTechUsrPwd;

    private final AxwayConnector axwayConnector;
    private final FigoConnector figoConnector;
    private final AxwaySessionRepository sessionRepository;

    public AxwayService(AxwayConnector axwayConnector, FigoConnector figoConnector, AxwaySessionRepository sessionRepository) {
        this.axwayConnector = axwayConnector;
        this.figoConnector = figoConnector;
        this.sessionRepository = sessionRepository;
    }

    public String loginAndObtainSession(Credentials credentials) {

        figoConnector.figoLogin(credentials)
                .map(token -> this.getSessionForTechnicalUser())
                .mapTry(session -> doesUserExit(credentials.getUsername(), session));

        return null;
    }

    public boolean doesUserExit(String username, String session) throws JsonProcessingException {
        ResponseEntity<AxwayUserQueryResponse> res = axwayConnector.userQuery(new AxwayUserQuery(username), session);
        System.out.println(res.getBody().getResponse().toString());
        return res.getBody().getResponse().getUsers().size() > 0;
    }

    public String getSessionForTechnicalUser() {
        LOG.info("Obtaining a Session for technical axway usr...");
        return sessionRepository.findById(axwayTechUsrLogin)
                .filter(axwaySession -> {
                    boolean isOlderThan3Months = axwaySession.getDateTime().isBefore(LocalDateTime.now().minusMonths(3));
                    if(isOlderThan3Months) LOG.info("Session is older than three months... we will refresh it.");
                    return !isOlderThan3Months;
                })
                .map(AxwaySession::getCookie)
                .orElseGet(() -> getAndSaveSession(axwayTechUsrLogin, axwayTechUsrPwd));
    }

    private String getAndSaveSession(String login, String password) {
        ResponseEntity<String> res = axwayConnector.retrieveLogin(login, password);
        String cookie = res.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        sessionRepository.save(new AxwaySession(axwayTechUsrLogin, cookie));
        return cookie;
    }


}
