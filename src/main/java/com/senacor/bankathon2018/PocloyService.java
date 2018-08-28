package com.senacor.bankathon2018;

import com.senacor.bankathon2018.connectors.FigoConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PocloyService {

  public static void main(String[] args) {
    SpringApplication.run(PocloyService.class, args);
  }

  @Bean
  FigoConnector buildFigoConnector() {
    new FigoConnector();
    return null;
  }
}
