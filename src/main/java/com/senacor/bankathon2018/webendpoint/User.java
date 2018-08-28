package com.senacor.bankathon2018.webendpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senacor.bankathon2018.service.LoginService;
import com.senacor.bankathon2018.service.TransactionService;
import com.senacor.bankathon2018.webendpoint.model.Credentials;
import com.senacor.bankathon2018.webendpoint.model.LoyaltyCode;
import io.vavr.control.Try;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
public class User {

    private final LoginService loginService;

  private final TransactionService transactionService;

  public User(LoginService loginService,
      TransactionService transactionService) {
        this.loginService = loginService;
    this.transactionService = transactionService;
    }


    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody Credentials credentials) {
        return loginService.isLoginViable(credentials) ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

  @PostMapping("/transactions")
  public ResponseEntity<String> transactions(@RequestBody Credentials credentials)
      throws JsonProcessingException {
    Try<List<LoyaltyCode>> codes = transactionService.getLoyaltyCodes(credentials);
    ObjectMapper objectMapper = new ObjectMapper();
    String codesAsJson;

    if (codes.isSuccess()) {
      codesAsJson = objectMapper.writeValueAsString(codes.get());
      return ResponseEntity.ok(codesAsJson);
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(codes.failed().getCause().getMessage());
    }
  }
}
