package com.senacor.bankathon2018;

import com.senacor.bankathon2018.service.NotificationService;
import me.figo.FigoConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PocloyService implements CommandLineRunner {

    @Value("${figo.client.id}")
    private String figoClientId;
    @Value("${figo.client.secret}")
    private String figoClientSecret;

    @Autowired
    private NotificationService notificationService;

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
        /*Credentials credentials = new Credentials("blalasaadri@googlemail.com", "Geheim");
        Try<ResponseEntity<UserId>> test = notificationService.test(credentials);
        test.onFailure(throwable -> {
            System.out.println(throwable);
        });
        test.onSuccess(succ -> {
            System.out.println(succ);
        });*/
    }
}
