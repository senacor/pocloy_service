package com.senacor.bankathon2018;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PocloyController {

  @RequestMapping("/")
  public String index() {
    return "Greetings from Spring Boot!";
  }

}
