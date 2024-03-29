package com.senacor.bankathon2018.service;

import com.senacor.bankathon2018.connectors.FigoConnector;
import com.senacor.bankathon2018.service.model.UserDto;
import com.senacor.bankathon2018.service.repository.FigoLoginTable;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.Credentials;
import io.vavr.control.Try;
import me.figo.FigoException;
import me.figo.internal.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;


@Service
public class LoginService {

    private final static Logger LOG = LoggerFactory.getLogger(LoginService.class);
    private final FigoConnector figoConnector;
    private final AxwayService axwayService;
    private final SubscribeService subscribeService;
    private final FigoLoginTable figoLoginTable;

    public LoginService(FigoConnector figoConnector, AxwayService axwayService,
                        SubscribeService subscribeService, FigoLoginTable figoLoginTable) {
        this.figoConnector = figoConnector;
        this.axwayService = axwayService;
        this.subscribeService = subscribeService;
        this.figoLoginTable = figoLoginTable;
    }

    public boolean isLoginViable(Credentials credentials, String deviceToken) {
        //TODO More Error Handling?
        Try<TokenResponse> figoLoginResponse = figoConnector.figoLogin(credentials);
        Try<String> axwayLoginResponse = axwayService.loginAndObtainSession(credentials);

        figoLoginResponse.onSuccess(token -> {
            figoLoginTable.save(new UserDto(credentials.getUsername(), credentials.getPassword()));
            LOG.info("Login into Figo was successful");
        });
        figoLoginResponse.onFailure(token -> LOG.info("Login into Figo was unsuccessful"));
        axwayLoginResponse.onSuccess(session -> LOG.info("Login into Axway was successful"));
        axwayLoginResponse.onFailure(session -> LOG.info("Login into Axway was unsuccessful"));

        manageFigoSubscriptions(credentials, figoLoginResponse);
        //manageAxwaySubscriptions(credentials, deviceToken, axwayLoginResponse);

        return figoLoginResponse.isSuccess() && axwayLoginResponse.isSuccess();
    }

    private void manageAxwaySubscriptions(Credentials credentials, String deviceToken,
                                          Try<String> axwayLoginResponse) {
        axwayLoginResponse
                .mapTry(session -> subscribeService
                        .subscripeToAxwayNotification(credentials.getUsername(), session, deviceToken))
                .onSuccess(successful -> {
                    if (successful) {
                        LOG.info("Subscribing to axway notifications was successful!");
                    } else {
                        LOG.info("Subscribing to axway notifications was unsuccessful!");
                    }
                }).onFailure(throwable -> {
            LOG.error("An error occured while we tried to subscribe to axway notifications", throwable);
        });
    }

    private void manageFigoSubscriptions(Credentials credentials,
                                         Try<TokenResponse> figoLoginResponse) {
        Try<Boolean> userIsCreatedToSubscription = figoLoginResponse.mapTry(tokenResponse ->
                subscribeService.subscribeUserToFigoNotification(tokenResponse.getAccessToken(),
                        credentials.getUsername()))
                .mapTry(wrappedResponse -> wrappedResponse
                        .getOrElseThrow(this::printNotificationErrorMessages));

        userIsCreatedToSubscription.onSuccess(b -> {
            if (b) {
                LOG.info("User is subscribed to FigoNotifications.");
            } else {
                LOG.info("User is not subscribed to FigoNotifications without further Exceptions");
            }
        });
    }

    private Throwable printNotificationErrorMessages(Throwable throwable) {
        if (throwable instanceof HttpClientErrorException) {
            HttpClientErrorException httpClientErrorException = (HttpClientErrorException) throwable;
            LOG.info("Could not subscribe user to FigoNotifications because:\n" +
                    httpClientErrorException.getResponseBodyAsString() + "\n" +
                    httpClientErrorException.getLocalizedMessage(), throwable);
        } else {
            LOG.info("Could not subscribe user to FigoNotifications because:\n" +
                    throwable.getLocalizedMessage(), throwable);
        }
        return throwable;
    }


    private void printErrMsg(Throwable throwable) {
        throwable.printStackTrace();
        if (throwable instanceof FigoException) {
            FigoException figoError = (FigoException) throwable;
            LOG.info(figoError.getErrorDescription());
        } else {
            LOG.info(throwable.getMessage());
        }
    }

    public Try<String> obtainAccessToken(Credentials credentials) {
        Try<TokenResponse> tokenResponses = figoConnector.figoLogin(credentials);
        return tokenResponses.map(res -> res.access_token);
    }

}
