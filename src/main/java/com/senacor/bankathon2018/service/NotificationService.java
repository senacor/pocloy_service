package com.senacor.bankathon2018.service;

import com.senacor.bankathon2018.connectors.AxwayConnector;
import com.senacor.bankathon2018.connectors.FigoConnector;
import com.senacor.bankathon2018.service.repository.FigoLoginTable;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class NotificationService {

    private final AxwayConnector axwayConnector;
    private final TransactionService transactionService;
    private final FigoLoginTable figoLoginTable;
    private final AxwayService axwayService;
    private final static Logger LOG = LoggerFactory.getLogger(NotificationService.class);


    public NotificationService(AxwayConnector axwayConnector, TransactionService transactionService, FigoLoginTable figoLoginTable, AxwayService axwayService) {
        this.axwayConnector = axwayConnector;
        this.transactionService = transactionService;
        this.figoLoginTable = figoLoginTable;
        this.axwayService = axwayService;
    }


    /**
     * @param username
     * @return
     */
    public boolean notifyUserAboutNewTransactions(String username) {
        int newLoyaltyCodes = figoLoginTable.findById(username)
                .map(usr -> transactionService.getAllTransactionsAfterLastKnownTransaction(new Credentials(usr.getUsername(), usr.getPassword())))
                .map(unknownTransactions -> unknownTransactions.getOrElse(Collections.emptyList()))
                .orElseGet(Collections::emptyList).size();

        if (newLoyaltyCodes > 0) {
            return axwayService.getSessionForTechnicalUser()
                    .mapTry(session -> axwayConnector.newTransactionNotification(username, session))
                    .mapTry(metaInfo -> metaInfo.getOrElseThrow(throwable -> {
                        LOG.error("Could not send Axway Notification:", throwable);
                        return new RuntimeException(throwable);
                    }))
                    .map(metaInfo -> metaInfo.getBody().getMeta().getCode() == 200)
                    .getOrElseGet(throwable -> {
                        LOG.error("Could not send Axway Notification:", throwable);
                        return false;
                    });
        }
        return false;
    }

}
