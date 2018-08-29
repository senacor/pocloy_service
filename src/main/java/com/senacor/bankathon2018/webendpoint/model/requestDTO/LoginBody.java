package com.senacor.bankathon2018.webendpoint.model.requestDTO;

public class LoginBody {

    private Credentials credentials;
    private String device_token;

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }
}
