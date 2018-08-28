package com.senacor.bankathon2018.service;

import com.senacor.bankathon2018.connectors.FigoConnector;
import com.senacor.bankathon2018.service.model.LoyaltyCode;
import com.senacor.bankathon2018.service.model.LoyaltyContent;
import com.senacor.bankathon2018.service.model.LoyaltyStatus;
import com.senacor.bankathon2018.service.repository.LoyaltyCodeRepository;
import com.senacor.bankathon2018.webendpoint.model.Credentials;
import com.senacor.bankathon2018.webendpoint.model.dto.LoyaltyCodeDTO;
import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.List;
import me.figo.models.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private final LoginService loginService;
  private final LoyaltyCodeRepository loyaltyCodeRepository;
  private final FigoConnector figoConnector;
  public static final String loyaltyCodeSuffixPattern = "LoyaltyCode. ";


  public TransactionService(LoginService loginService,
      LoyaltyCodeRepository loyaltyCodeRepository,
      FigoConnector figoConnector) {
    this.loginService = loginService;
    this.loyaltyCodeRepository = loyaltyCodeRepository;
    this.figoConnector = figoConnector;
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
      }
    }

    //query figo for new loyaltyCodes
    loginService
        .obtainAccessToken(credentials)
        .map(token -> figoConnector.getTransactions(token, null, true))
        .onSuccess(transactionObject -> {
          for (Transaction transaction : transactionObject.getTransactions()) {
            //only add transactions with LoyaltyCodes
            if (transaction.getBookingText() != null &&
                transaction.getBookingText().contains(loyaltyCodeSuffixPattern)) {
              //Save newly found transaction
              String loyaltyCodeText = transaction.getBookingText()
                  .split(loyaltyCodeSuffixPattern)[1].split(" ")[0];
              LoyaltyCode newLoyaltyCode = new LoyaltyCode(loyaltyCodeText, LoyaltyStatus.packed,
                  LoyaltyContent.unknown, transaction.getBookingDate(),
                  transaction.getTransactionId(), credentials.getUsername());
              loyaltyCodeRepository.save(newLoyaltyCode);
              result.add(new LoyaltyCodeDTO(newLoyaltyCode));
            }
          }
        });
    return Try.of(() -> result);
  }

}
