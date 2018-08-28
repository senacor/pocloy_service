package com.senacor.bankathon2018.webendpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senacor.bankathon2018.service.LoginService;
import com.senacor.bankathon2018.service.TransactionService;
import com.senacor.bankathon2018.webendpoint.model.Credentials;
import com.senacor.bankathon2018.webendpoint.model.LoyaltyCodeWithCredentials;
import com.senacor.bankathon2018.webendpoint.model.dto.LoyaltyCodeDTO;
import io.vavr.control.Try;

import java.util.List;

import me.figo.FigoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.google.common.base.Predicates.instanceOf;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

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
        Try<List<LoyaltyCodeDTO>> codes = transactionService.getLoyaltyCodes(credentials);
        ObjectMapper objectMapper = new ObjectMapper();
        String codesAsJson;

        if (codes.isSuccess()) {
            codesAsJson = objectMapper.writeValueAsString(codes.get());
            return ResponseEntity.ok(codesAsJson);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(buildErrMsg(codes.getCause()));
        }
    }

    private String buildErrMsg(Throwable throwable) {
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
