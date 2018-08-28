package com.senacor.bankathon2018.service;

import com.senacor.bankathon2018.connectors.FigoConnector;
import com.senacor.bankathon2018.webendpoint.model.Credentials;
import com.senacor.bankathon2018.webendpoint.model.LoyaltyCode;
import com.senacor.bankathon2018.webendpoint.model.LoyaltyContent;
import com.senacor.bankathon2018.webendpoint.model.LoyaltyStatus;
import io.vavr.control.Try;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.figo.models.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final LoginService loginService;
    private final FigoConnector figoConnector;

    public TransactionService(LoginService loginService, FigoConnector figoConnector) {
        this.loginService = loginService;
        this.figoConnector = figoConnector;
    }

    public Try<List<LoyaltyCode>> getLoyaltyCodes(Credentials credentials) {
    /*if (loginService.isLoginViable(credentials)) {
      throw new RuntimeException("Login failed");
    }*/
        List<LoyaltyCode> result = new ArrayList<>();

        //Todo query known loyaltyCodes from DB

        //Todo query figo for new loyaltyCodes
        loginService
                .obtainAccessToken(credentials)
                .map(token -> figoConnector.getTransactions(token, null, true))
                .onSuccess(transactionObject -> {
                    System.out.println(transactionObject.getTransactions().size());
                    for (Transaction transaction : transactionObject.getTransactions()) {
                        System.out.println("{");
                        System.out.println("BookingText=" + transaction.getBookingText());
                        System.out.println("Amount=" + transaction.getAmount());
                        System.out.println("}");
                    }
                })
                .onFailure(err -> {
                    System.out.println(err.getMessage());
                });

        //Update DB with new transactions

        //query DB for transactions

        LoyaltyCode loyaltyCode = new LoyaltyCode(
                "ae546c90-c381-4e73-b38d-0565f1200b94",
                LoyaltyStatus.packed.toString(),
                LoyaltyContent.unknown.toString(),
                Calendar.getInstance().getTime());

        LoyaltyCode loyaltyCodeUnpacked = new LoyaltyCode(
                "96e86953-c71a-40f0-af60-5bd83d7a77c0",
                LoyaltyStatus.unpacked.toString(),
                LoyaltyContent.sun.toString(),
                Calendar.getInstance().getTime());

        result.add(loyaltyCode);
        result.add(loyaltyCodeUnpacked);

        return Try.of(() -> result);
    }

}
