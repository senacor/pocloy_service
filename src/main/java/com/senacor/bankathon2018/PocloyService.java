package com.senacor.bankathon2018;

import me.figo.FigoConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PocloyService {

  @Value("${figo.client.id}")
  private String figoClientId;
  @Value("${figo.client.secret}")
  private String figoClientSecret;

  public static void main(String[] args) {
    SpringApplication.run(PocloyService.class, args);
  }

  @Bean
  FigoConnection buildFigoConnector() {
    return new FigoConnection(figoClientId, figoClientSecret, "");
  }

  @Bean
  RestTemplate buildResttemplate(RestTemplateBuilder builder) {
    return builder.setReadTimeout(15000).build();
  }
}
