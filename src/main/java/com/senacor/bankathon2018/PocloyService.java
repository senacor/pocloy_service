package com.senacor.bankathon2018;

import com.senacor.bankathon2018.service.AxwayService;
import com.senacor.bankathon2018.service.repository.AxwaySessionRepository;
import io.vavr.control.Try;
import me.figo.FigoConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.HttpClientErrorException;
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
        return builder.setReadTimeout(30000).build();
    }

    @Override
    public void run(String... args) throws Exception {
        Try<String> createUser = axwayService.getSessionForTechnicalUser()
                .map(session -> axwayService.createUser("deleteMe", "NotVerySecure", session));
        createUser.onSuccess(session -> System.out.println("Session=" + session));
        createUser.onFailure(throwable -> {
            if (throwable instanceof HttpClientErrorException) {
                HttpClientErrorException httpClientErrorException = (HttpClientErrorException) throwable;
                System.out.println(httpClientErrorException.getResponseBodyAsString());
            } else {
                System.out.println(throwable.getLocalizedMessage());
            }
        });
    }
}
