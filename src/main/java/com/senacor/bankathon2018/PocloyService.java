package com.senacor.bankathon2018;

import com.senacor.bankathon2018.connectors.AxwayConnector;
import com.senacor.bankathon2018.service.AxwayService;
import com.senacor.bankathon2018.service.repository.AxwaySessionRepository;
import me.figo.FigoConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PocloyService implements CommandLineRunner {

  @Value("${figo.client.id}")
  private String figoClientId;
  @Value("${figo.client.secret}")
  private String figoClientSecret;

  @Autowired
  private AxwayService axwayService;
  @Autowired
  private AxwaySessionRepository repo;

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

    @Override
    public void run(String... args) throws Exception {
        String session = axwayService.getSessionForTechnicalUser();
        boolean b = axwayService.doesUserExit("pocloy-axway-api", session);
        System.out.println(b);
    }
}
