package com.senacor.bankathon2018.webendpoint;

import com.senacor.bankathon2018.webendpoint.model.Credentials;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class User {

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody Credentials credentials) {
        return ResponseEntity.ok().build();
    }
}
