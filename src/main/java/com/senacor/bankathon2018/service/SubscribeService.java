package com.senacor.bankathon2018.service;

import com.senacor.bankathon2018.connectors.AxwayConnector;
import com.senacor.bankathon2018.connectors.FigoConnector;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class SubscribeService {

    private final static Logger LOG = LoggerFactory.getLogger(SubscribeService.class);
    private final AxwayConnector axwayConnector;
    private final FigoConnector figoConnector;

    public SubscribeService(AxwayConnector axwayConnector, FigoConnector figoConnector) {
        this.axwayConnector = axwayConnector;
        this.figoConnector = figoConnector;
    }


    public Boolean subscripeToAxwayNotification(String username, String session, String deviceToken) {
        return axwayConnector.subscribeToAxwayNotifications(username, session, deviceToken)
                .mapTry(res -> {
                    LOG.info(res.toString());
                    return true;
                }).getOrElseGet(throwable -> {
                    if (throwable instanceof HttpClientErrorException) {
                        HttpClientErrorException httpClientErrorException = (HttpClientErrorException) throwable;
                        LOG.info("Could not subscribe user to Axway because:\n" +
                                httpClientErrorException.getResponseBodyAsString() + "\n" +
                                httpClientErrorException.getLocalizedMessage(), throwable);
                    } else {
                        LOG.info("Could not subscribe user to Axway because:\n" +
                                throwable.getLocalizedMessage(), throwable);
                    }
                    return false;
                });
    }


    public Try<Boolean> subscribeUserToFigoNotification(String accessToken, String username) {
        Boolean hasFigoNotification = hasFigoNotification(accessToken);
        return Try.of(() -> {
            if (!hasFigoNotification) {
                return createFigoSubscription(accessToken, username).getOrElseThrow(throwable -> {
                    LOG.error("Error while trying to create a FigoSubscription", throwable);
                    if (throwable instanceof HttpClientErrorException) {
                        HttpClientErrorException httpClientErrorException = (HttpClientErrorException) throwable;
                        LOG.error(httpClientErrorException.getResponseBodyAsString());
                    }
                    throw new RuntimeException(throwable.getMessage(), throwable);
                });
            }
            return true;
        });
    }


    private Boolean hasFigoNotification(String accessToken) {
        Try<Boolean> getNotificationResponse = figoConnector.getNotifications(accessToken)
                .mapTry(res -> res.getBody().getNotifications()
                        .stream()
                        .peek(logRes -> LOG.info(res.toString()))
                        .anyMatch(notification -> notification.getObserve_key().equals("/rest/transactions")));

        return getNotificationResponse.getOrElseGet(throwable -> {
            LOG.info("Could not get FigoSubscriptions:", throwable);
            return false;
        });
    }

    private Try<Boolean> createFigoSubscription(String accessToken, String username) {
        return figoConnector.createSubscription(username, accessToken)
                .mapTry(res -> {
                    LOG.info(res.toString());
                    return true;
                });
    }
}
