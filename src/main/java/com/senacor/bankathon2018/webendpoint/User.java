package com.senacor.bankathon2018.webendpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senacor.bankathon2018.service.LoginService;
import com.senacor.bankathon2018.webendpoint.model.Credentials;
import com.senacor.bankathon2018.webendpoint.model.LoyaltyCode;
import com.senacor.bankathon2018.webendpoint.model.LoyaltyContent;
import com.senacor.bankathon2018.webendpoint.model.LoyaltyStatus;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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

    public User(LoginService loginService) {
        this.loginService = loginService;
    }


    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody Credentials credentials) {
        return loginService.isLoginViable(credentials) ?
                ResponseEntity.ok().build() :
                ResponseEntity.badRequest().build();
    }

  @GetMapping("/transactions")
  public String transactions() throws JsonProcessingException {
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

    List<LoyaltyCode> codes = new ArrayList<>();
    codes.add(loyaltyCode);
    codes.add(loyaltyCodeUnpacked);

    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(codes);
  }
}
