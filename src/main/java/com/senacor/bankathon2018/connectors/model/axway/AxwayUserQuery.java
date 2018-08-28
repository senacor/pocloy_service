package com.senacor.bankathon2018.connectors.model.axway;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("where")
public class AxwayUserQuery {

    private String username;

    public AxwayUserQuery(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
