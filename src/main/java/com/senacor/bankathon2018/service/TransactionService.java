package com.senacor.bankathon2018.service;

import com.senacor.bankathon2018.webendpoint.model.Credentials;
import com.senacor.bankathon2018.webendpoint.model.LoyaltyCode;
import com.senacor.bankathon2018.webendpoint.model.LoyaltyContent;
import com.senacor.bankathon2018.webendpoint.model.LoyaltyStatus;
import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private final LoginService loginService;

  public TransactionService(LoginService loginService) {
    this.loginService = loginService;
  }

  public Try<List<LoyaltyCode>> getLoyaltyCodes(Credentials credentials) {
    if (loginService.isLoginViable(credentials)) {
      throw new RuntimeException("Login failed");
    }
    List<LoyaltyCode> result = new ArrayList<>();

    //Todo query known loyaltyCodes from DB

    //Todo query figo for new loyaltyCodes

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
