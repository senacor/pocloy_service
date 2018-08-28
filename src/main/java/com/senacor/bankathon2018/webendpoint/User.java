package com.senacor.bankathon2018.webendpoint;

import com.senacor.bankathon2018.connectors.FigoConnector;
import com.senacor.bankathon2018.service.LoginService;
import com.senacor.bankathon2018.webendpoint.model.Credentials;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
