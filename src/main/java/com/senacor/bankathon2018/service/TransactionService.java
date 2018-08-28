package com.senacor.bankathon2018.service;

import com.senacor.bankathon2018.connectors.FigoConnector;
import com.senacor.bankathon2018.connectors.model.Transaction;
import com.senacor.bankathon2018.service.model.BoughtVoucher;
import com.senacor.bankathon2018.service.model.LoyaltyCode;
import com.senacor.bankathon2018.service.model.LoyaltyContent;
import com.senacor.bankathon2018.service.model.LoyaltyStatus;
import com.senacor.bankathon2018.service.repository.BoughtVoucherRepository;
import com.senacor.bankathon2018.service.repository.LoyaltyCodeRepository;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.Credentials;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.LoyaltyCodeWithCredentials;
import com.senacor.bankathon2018.webendpoint.model.returnDTO.BoughtVoucherDTO;
import com.senacor.bankathon2018.webendpoint.model.returnDTO.LoyaltyCodeDTO;
import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private static final String loyaltyCodeSuffixPattern = ".+[lL]oyalty[cC]ode. ([a-zA-Z0-9\\-]+).+";
  private final LoginService loginService;
  private final LoyaltyCodeRepository loyaltyCodeRepository;
  private final FigoConnector figoConnector;
  private final BoughtVoucherRepository boughtVoucherRepository;


  public TransactionService(LoginService loginService,
      LoyaltyCodeRepository loyaltyCodeRepository,
      FigoConnector figoConnector,
      BoughtVoucherRepository boughtVoucherRepository) {
    this.loginService = loginService;
    this.loyaltyCodeRepository = loyaltyCodeRepository;
    this.figoConnector = figoConnector;
    this.boughtVoucherRepository = boughtVoucherRepository;
  }

  public Try<List<LoyaltyCodeDTO>> getLoyaltyCodes(Credentials credentials) {
    List<LoyaltyCodeDTO> result = new ArrayList<>();
    LoyaltyCode codeWithMaxTxCode = null;

    //query known loyaltyCodes from DB
    for (LoyaltyCode loyaltyCode : loyaltyCodeRepository.findByUser(credentials.getUsername())) {
        if (codeWithMaxTxCode == null || codeWithMaxTxCode.getPaymentDate()
            .isBefore(loyaltyCode.getPaymentDate())) {
          codeWithMaxTxCode = loyaltyCode;
        }
        result.add(new LoyaltyCodeDTO(loyaltyCode));
    }

    String lastTxCode = codeWithMaxTxCode != null ? codeWithMaxTxCode.getPaymentTransactionId() : null;

    //query figo for new loyaltyCodes
    return loginService
        .obtainAccessToken(credentials)
        .map(token -> figoConnector.getTransactions(token, lastTxCode, true))
        .map(transactionObject -> {
          for (Transaction transaction : transactionObject.getTransactions()) {
            //only add transactions with LoyaltyCodes
            Pattern pattern = Pattern.compile(loyaltyCodeSuffixPattern);
            if (transaction.getPurpose() != null) {
              Matcher matcher = pattern.matcher(transaction.getPurpose());
              if (matcher.matches()) {
                //Save newly found transaction
                String loyaltyCodeText = matcher.group(1);
                LoyaltyCode newLoyaltyCode = new LoyaltyCode(loyaltyCodeText, LoyaltyStatus.packed,
                    LoyaltyContent.unknown, transaction.getBooking_date(),
                    transaction.getTransaction_id(), credentials.getUsername());
                //Filter transactions that where sent and received by the same user
                if (!loyaltyCodeRepository.existsById(newLoyaltyCode.getPaymentTransactionId())) {
                  loyaltyCodeRepository.save(newLoyaltyCode);
                  result.add(new LoyaltyCodeDTO(newLoyaltyCode));
                }
              }
            }
          }
          return result;
        });
  }

  public LoyaltyCodeDTO unpackAndReturnLoyaltyCode(
      LoyaltyCodeWithCredentials loyaltyCodeWithCredentials) {
    if (!loginService.isLoginViable(loyaltyCodeWithCredentials.getCredentials())) {
      throw new IllegalArgumentException("Wrong Credentials");
    }
    return loyaltyCodeRepository
            .findById(loyaltyCodeWithCredentials.getCodeId())
            .map(this::createContentForLoyaltyCodeDTO)
            .map(LoyaltyCodeDTO::new)
            .orElseThrow(() -> new IllegalArgumentException("The codeId could not be found or is already unpacked!"));
  }

  private LoyaltyCode createContentForLoyaltyCodeDTO(LoyaltyCode loyaltyCodeToUnpack) {

    if (loyaltyCodeToUnpack.getStatus().equals(LoyaltyStatus.unpacked)) {
      return null;
    }

    //TODO: Maybe make select content after pattern
    int pick = 1 +  new Random(Calendar.getInstance().getTimeInMillis())
            .nextInt(LoyaltyContent.values().length - 1);
    LoyaltyContent surpriseContent = LoyaltyContent.values()[pick];

    loyaltyCodeToUnpack.setContent(surpriseContent);
    loyaltyCodeToUnpack.setStatus(LoyaltyStatus.unpacked);
    return loyaltyCodeRepository.save(loyaltyCodeToUnpack);
  }

  public List<BoughtVoucherDTO> getUserVouchers(Credentials credentials) {
    if (!loginService.isLoginViable(credentials)) {
      throw new IllegalArgumentException("Wrong Credentials");
    }
    List<BoughtVoucherDTO> voucherDTOs = new ArrayList<>();
    for (BoughtVoucher boughtVoucherOfUser : boughtVoucherRepository
        .findByUser(credentials.getUsername())) {
      voucherDTOs.add(new BoughtVoucherDTO(boughtVoucherOfUser));
    }
    return voucherDTOs;
  }

}
