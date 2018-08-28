package com.senacor.bankathon2018.service;

import com.senacor.bankathon2018.connectors.FigoConnector;
import com.senacor.bankathon2018.connectors.model.Transaction;
import com.senacor.bankathon2018.service.model.LoyaltyCode;
import com.senacor.bankathon2018.service.model.LoyaltyContent;
import com.senacor.bankathon2018.service.model.LoyaltyStatus;
import com.senacor.bankathon2018.service.repository.LoyaltyCodeRepository;
import com.senacor.bankathon2018.webendpoint.model.Credentials;
import com.senacor.bankathon2018.webendpoint.model.LoyaltyCodeWithCredentials;
import com.senacor.bankathon2018.webendpoint.model.dto.LoyaltyCodeDTO;
import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
            .isBefore(loyaltyCode.getPaymentDate())) {
          codeWithMaxTxCode = loyaltyCode;
        }
        result.add(new LoyaltyCodeDTO(loyaltyCode));
      }
    }

    String lastTxCode = codeWithMaxTxCode != null ? codeWithMaxTxCode.getPaymentTransactionId() : null;

    //query figo for new loyaltyCodes
    return loginService
        .obtainAccessToken(credentials)
        .map(token -> figoConnector.getTransactions(token, lastTxCode, true))
        .map(transactionObject -> {
          for (Transaction transaction : transactionObject.getTransactions()) {
            //only add transactions with LoyaltyCodes
            if (transaction.getSepa_remittance_info() != null &&
                transaction.getSepa_remittance_info().contains(loyaltyCodeSuffixPattern)) {
              //Save newly found transaction
              String loyaltyCodeText = transaction.getSepa_remittance_info()
                  .split(loyaltyCodeSuffixPattern)[1].split(" ")[0];
              LoyaltyCode newLoyaltyCode = new LoyaltyCode(loyaltyCodeText, LoyaltyStatus.packed,
                  LoyaltyContent.unknown, transaction.getBooking_date(),
                  transaction.getTransaction_id(), credentials.getUsername());
              loyaltyCodeRepository.save(newLoyaltyCode);
              result.add(new LoyaltyCodeDTO(newLoyaltyCode));
            }
          }
          return result;
        });
  }

  public LoyaltyCodeDTO unpackAndReturnLoyaltyCode(
      LoyaltyCodeWithCredentials loyaltyCodeWithCredentials) {
    if (!loginService.isLoginViable(loyaltyCodeWithCredentials.getCredentials())) {
      return null;
    }
    LoyaltyCode loyaltyCodeToUnpack = loyaltyCodeRepository
        .findById(loyaltyCodeWithCredentials.getCodeId()).get();

    //TODO: Maybe make select content after pattern
    int pick = new Random().nextInt(1 + LoyaltyContent.values().length);
    LoyaltyContent surpriseContent = LoyaltyContent.values()[pick];

    loyaltyCodeToUnpack.setContent(surpriseContent);
    loyaltyCodeToUnpack.setStatus(LoyaltyStatus.unpacked);
    loyaltyCodeRepository.save(loyaltyCodeToUnpack);
    return new LoyaltyCodeDTO(loyaltyCodeToUnpack);
  }

}
