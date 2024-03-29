package com.senacor.bankathon2018.webendpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senacor.bankathon2018.service.LoginService;
import com.senacor.bankathon2018.service.TransactionService;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.Credentials;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.ExchangeOfferDTO;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.ExchangeOfferToConsumeWithCredentials;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.ExchangeOffersWithCredentials;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.LoginBody;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.LoyaltyCodeWithCredentials;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.VoucherTypeWithCredentials;
import com.senacor.bankathon2018.webendpoint.model.returnDTO.BoughtVoucherDTO;
import com.senacor.bankathon2018.webendpoint.model.returnDTO.LoyaltyCodeDTO;
import io.vavr.control.Try;
import java.util.List;
import me.figo.FigoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
public class User {

    private final LoginService loginService;

    private final TransactionService transactionService;

  private final static Logger LOG = LoggerFactory.getLogger(User.class);

    public User(LoginService loginService,
                TransactionService transactionService) {
        this.loginService = loginService;
        this.transactionService = transactionService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginBody loginBody) {
      return loginService.isLoginViable(loginBody.getCredentials(), loginBody.getDevice_token()) ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

    @PostMapping("/transactions")
    public ResponseEntity<String> transactions(@RequestBody Credentials credentials)
            throws JsonProcessingException {
        Try<List<LoyaltyCodeDTO>> codes = transactionService.getLoyaltyCodes(credentials);
        ObjectMapper objectMapper = new ObjectMapper();
        String codesAsJson;

        if (codes.isSuccess()) {
            codesAsJson = objectMapper.writeValueAsString(codes.get());
            return ResponseEntity.ok(codesAsJson);
        } else {
          String errorMsg = buildErrMsg(codes.getCause());
          LOG.error(errorMsg);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorMsg);
        }
    }

    private String buildErrMsg(Throwable throwable) {
      throwable.printStackTrace();
        if(throwable instanceof FigoException) {
            FigoException figoError = (FigoException) throwable;
            return figoError.getErrorDescription();
        } else {
            return throwable.getMessage();
        }
    }

    @PostMapping("/unpack")
    public ResponseEntity<String> unpack(
            @RequestBody LoyaltyCodeWithCredentials loyaltyCodeWithCredentials)
            throws JsonProcessingException {
        Try<LoyaltyCodeDTO> wrappedResult = Try.of(() -> transactionService
                .unpackAndReturnLoyaltyCode(loyaltyCodeWithCredentials));
        ObjectMapper objectMapper = new ObjectMapper();
        if (wrappedResult.isSuccess()) {
            return ResponseEntity.ok(objectMapper.writeValueAsString(wrappedResult.get()));
        } else {
          String errorMsg = buildErrMsg(wrappedResult.getCause());
          LOG.error(errorMsg);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorMsg);
        }
    }

    @PostMapping("/redeemVoucher")
    public ResponseEntity<String> redeemVoucher(
        @RequestBody VoucherTypeWithCredentials voucherTypeWithCredentials)
        throws JsonProcessingException {
      Try<BoughtVoucherDTO> wrappedResult = Try.of(() -> transactionService
          .buyVoucher(voucherTypeWithCredentials));
      ObjectMapper objectMapper = new ObjectMapper();
      if (wrappedResult.isSuccess()) {
        return ResponseEntity.ok(objectMapper.writeValueAsString(wrappedResult.get()));
      } else {
        String errorMsg = buildErrMsg(wrappedResult.getCause());
        LOG.error(errorMsg);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorMsg);
      }
    }

    @PostMapping("/vouchers")
    public ResponseEntity<String> vouchers(
        @RequestBody Credentials credentials) throws JsonProcessingException {
        Try<List<BoughtVoucherDTO>> wrappedResult = Try.of(() -> transactionService
            .getUserVouchers(credentials));
        ObjectMapper objectMapper = new ObjectMapper();
        if (wrappedResult.isSuccess()) {
            return ResponseEntity.ok(objectMapper.writeValueAsString(wrappedResult.get()));
        } else {
          String errorMsg = buildErrMsg(wrappedResult.getCause());
          LOG.error(errorMsg);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorMsg);
        }
    }

  @GetMapping("/consumeVoucher")
  public ResponseEntity<String> consumeVoucher(
      @RequestParam Integer voucherId, @RequestParam String user) throws JsonProcessingException {
    Try<Void> wrappedResult = Try.of(() -> transactionService
        .consumeVoucher(voucherId, user));
    ObjectMapper objectMapper = new ObjectMapper();
    if (wrappedResult.isSuccess()) {
      return ResponseEntity.ok(objectMapper.writeValueAsString("Voucher consumed."));
    } else {
      String errorMsg = buildErrMsg(wrappedResult.getCause());
      LOG.error(errorMsg);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(errorMsg);
    }
  }

  @PostMapping("/setMyExchangeOffers")
  public ResponseEntity<String> setMyExcangeOffers(
      @RequestBody ExchangeOffersWithCredentials exchangeOffersWithCredentials) {
    Try<Void> wrappedResult = Try.of(() -> transactionService
        .setMyExchangeOffers(exchangeOffersWithCredentials));
    if (wrappedResult.isSuccess()) {
      return ResponseEntity.ok().build();
    } else {
      String errorMsg = buildErrMsg(wrappedResult.getCause());
      LOG.error(errorMsg);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(errorMsg);
    }
  }

  @PostMapping("/getMyExchangeOffers")
  public ResponseEntity<String> getMyExcangeOffers(
      @RequestBody Credentials credentials) throws JsonProcessingException {
    Try<List<ExchangeOfferDTO>> wrappedResult = Try.of(() -> transactionService
        .getMyExchangeOffers(credentials));
    ObjectMapper objectMapper = new ObjectMapper();
    if (wrappedResult.isSuccess()) {
      return ResponseEntity.ok(objectMapper.writeValueAsString(wrappedResult.get()));
    } else {
      String errorMsg = buildErrMsg(wrappedResult.getCause());
      LOG.error(errorMsg);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(errorMsg);
    }
  }

  @PostMapping("/getOtherExchangeOffers")
  public ResponseEntity<String> getOtherExcangeOffers(
      @RequestBody Credentials credentials) throws JsonProcessingException {
    Try<List<ExchangeOfferDTO>> wrappedResult = Try.of(() -> transactionService
        .getOtherExchangeOffers(credentials));
    ObjectMapper objectMapper = new ObjectMapper();
    if (wrappedResult.isSuccess()) {
      return ResponseEntity.ok(objectMapper.writeValueAsString(wrappedResult.get()));
    } else {
      String errorMsg = buildErrMsg(wrappedResult.getCause());
      LOG.error(errorMsg);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(errorMsg);
    }
  }

  @PostMapping("/consumeExchangeOffer")
  public ResponseEntity<String> consumeExchangeOffer(
      @RequestBody ExchangeOfferToConsumeWithCredentials exchangeOfferToConsumeWithCredentials) {
    Try<Void> wrappedResult = Try.of(() -> transactionService
        .consumeExchangeOffer(exchangeOfferToConsumeWithCredentials));
    if (wrappedResult.isSuccess()) {
      return ResponseEntity.ok().build();
    } else {
      String errorMsg = buildErrMsg(wrappedResult.getCause());
      LOG.error(errorMsg);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(errorMsg);
    }
  }

}
