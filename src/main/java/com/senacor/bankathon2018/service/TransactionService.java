package com.senacor.bankathon2018.service;

import com.senacor.bankathon2018.service.model.LoyaltyCode;
import com.senacor.bankathon2018.service.model.LoyaltyContent;
import com.senacor.bankathon2018.service.model.LoyaltyStatus;
import com.senacor.bankathon2018.service.repository.LoyaltyCodeRepository;
import com.senacor.bankathon2018.webendpoint.model.Credentials;
import com.senacor.bankathon2018.webendpoint.model.dto.LoyaltyCodeDTO;
import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private final LoginService loginService;
  private final LoyaltyCodeRepository loyaltyCodeRepository;

  public TransactionService(LoginService loginService,
      LoyaltyCodeRepository loyaltyCodeRepository) {
    this.loginService = loginService;
    this.loyaltyCodeRepository = loyaltyCodeRepository;
  }

  public Try<List<LoyaltyCodeDTO>> getLoyaltyCodes(Credentials credentials) {
    List<LoyaltyCodeDTO> result = new ArrayList<>();
    LoyaltyCode codeWithMaxTxCode = null;

    //query known loyaltyCodes from DB
    for (LoyaltyCode loyaltyCode : loyaltyCodeRepository.findAll()) {
      //TODO maybe replace if with "byUsername" query method in repository
      if (credentials.getUsername().equals(loyaltyCode.getUser())) {
        if (codeWithMaxTxCode == null || codeWithMaxTxCode.getPaymentDate()
            .before(loyaltyCode.getPaymentDate())) {
          codeWithMaxTxCode = loyaltyCode;
        }
        result.add(new LoyaltyCodeDTO(loyaltyCode));
        System.out.println(
            "Found code in DB " + loyaltyCode.getLoyaltyCode() + " with content " + loyaltyCode
                .getContent());
      }
    }



    //Todo query figo for new loyaltyCodes

    //Update DB with new transactions
    loyaltyCodeRepository.save(new LoyaltyCode(
        UUID.randomUUID().toString(),
        LoyaltyStatus.unpacked,
        LoyaltyContent.sun,
        Calendar.getInstance().getTime(),
        UUID.randomUUID().toString(),
        credentials.getUsername()));

    return Try.of(() -> result);
  }

}
