package com.senacor.bankathon2018.connectors;

import com.senacor.bankathon2018.webendpoint.model.Credentials;
import io.vavr.control.Try;
import me.figo.FigoConnection;
import me.figo.FigoException;
import me.figo.internal.TokenResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FigoConnector {

    private final FigoConnection figoConnection;


    public FigoConnector(FigoConnection figoConnection) {
        this.figoConnection = figoConnection;
    }

    public Try<TokenResponse> figoLogin(Credentials credentials) {
        return Try.of(() -> figoConnection.credentialLogin(credentials.getUsername(), credentials.getPassword()));
    }

}
