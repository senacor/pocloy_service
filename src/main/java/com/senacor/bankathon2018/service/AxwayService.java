package com.senacor.bankathon2018.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senacor.bankathon2018.connectors.AxwayConnector;
import com.senacor.bankathon2018.connectors.FigoConnector;
import com.senacor.bankathon2018.connectors.model.axway.user.AxwayUserQuery;
import com.senacor.bankathon2018.connectors.model.axway.user.AxwayUserQueryResponse;
import com.senacor.bankathon2018.service.model.AxwaySession;
import com.senacor.bankathon2018.service.repository.AxwaySessionRepository;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.Credentials;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
                .map(Try::get)
                .mapTry(session -> doesUserExit(credentials.getUsername(), session));

        return null;
    }

    private boolean doesUserExit(String username, String session) throws JsonProcessingException {
        ResponseEntity<AxwayUserQueryResponse> res = axwayConnector.userQuery(new AxwayUserQuery(username), session);
        return res.getBody().getResponse().getUsers().size() > 0;
    }

    private boolean createUser(String login, String password, String techUsrSession) {
        return axwayConnector.createUser(login, password, techUsrSession).isSuccess();
    }
    

    public Try<String> getSessionForTechnicalUser() {
        LOG.info("Obtaining a Session for technical axway usr...");
        return Try.of(() ->
                sessionRepository.findById(axwayTechUsrLogin)
                        .filter(axwaySession -> {
                            boolean isOlderThan3Months = axwaySession.getDateTime().isBefore(LocalDateTime.now().minusMonths(3));
                            if (isOlderThan3Months)
                                LOG.info("Session is older than three months... we will refresh it.");
                            return !isOlderThan3Months;
                        })
                        .map(AxwaySession::getCookie)
                        .orElseGet(() -> getAndSaveSession(axwayTechUsrLogin, axwayTechUsrPwd).get())
        );
    }

    private Try<String> getAndSaveSession(String login, String password) {
        return axwayConnector.retrieveLogin(login, password).map(res -> {
            String cookie = res.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
            sessionRepository.save(new AxwaySession(login, cookie));
            return cookie;
        });
    }


}
