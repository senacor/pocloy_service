package com.senacor.bankathon2018.service;

import com.senacor.bankathon2018.connectors.FigoConnector;
import com.senacor.bankathon2018.connectors.model.UserId;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.Credentials;
import io.vavr.control.Try;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final FigoConnector figoConnector;
    private final LoginService loginService;

    public NotificationService(FigoConnector figoConnector, LoginService loginService) {
        this.figoConnector = figoConnector;
        this.loginService = loginService;
    }

    public Try<ResponseEntity<UserId>> test(Credentials credentials) {
        return loginService.obtainAccessToken(credentials)
                .map(figoConnector::getUserId);
    }
}
