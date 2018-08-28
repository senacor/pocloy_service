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

    public Try<String> loginAndObtainSession(Credentials credentials) {
        String login = credentials.getUsername();
        String password = credentials.getPassword();

        return figoConnector.figoLogin(credentials)
                .map(token -> this.getSessionForTechnicalUser())
                .map(Try::get)
                .mapTry(session -> doesUserExit(login, session))
                .mapTry(doesExit -> {
                    if (doesExit) {
                        return getSession(login, password)
                                .getOrElseThrow(throwable -> {
                                    LOG.error("Error while trying to obtain a session. User did exit before.", throwable);
                                    throw new RuntimeException(throwable.getMessage(), throwable);
                                });
                    } else if (createUser(login, password)) {
                        return getSession(login, password)
                                .getOrElseThrow(throwable -> {
                                    LOG.error("Error while trying to obtain a session. User did not exit before.", throwable);
                                    throw new RuntimeException(throwable.getMessage(), throwable);
                                });
                    } else {
                        throw new RuntimeException("Could not create new User");
                    }
                });
    }


    private boolean doesUserExit(String username, String session) throws JsonProcessingException {
        ResponseEntity<AxwayUserQueryResponse> res = axwayConnector.userQuery(new AxwayUserQuery(username), session);
        long count = res.getBody().getResponse().getUsers()
                .stream()
                .filter(user -> username.equals(user.getUsername()))
                .count();
        return count > 0;
    }


    private boolean createUser(String login, String password) {
        return getSessionForTechnicalUser()
                .map(session -> createUser(login, password, session))
                .getOrElseThrow(throwable -> {
                    throw new RuntimeException(throwable);
                });
    }

    private boolean createUser(String login, String password, String techUsrSession) {
        return axwayConnector.createUser(login, password, techUsrSession).isSuccess();
    }


    private Try<String> getSessionForTechnicalUser() {
        LOG.info("Obtaining a Session for technical axway usr...");
        return getSession(axwayTechUsrLogin, axwayTechUsrPwd);
    }

    private Try<String> getSession(String login, String password) {
        return Try.of(() ->
                sessionRepository.findById(login)
                        .filter(axwaySession -> {
                            boolean isOlderThan3Months = axwaySession.getDateTime().isBefore(LocalDateTime.now().minusMonths(3));
                            if (isOlderThan3Months)
                                LOG.info("Session is older than three months... we will refresh it.");
                            return !isOlderThan3Months;
                        })
                        .map(AxwaySession::getCookie)
                        .orElseGet(() -> getAndSaveSession(login, password).get())
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
