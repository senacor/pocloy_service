package com.senacor.bankathon2018.connectors.model.axway.user;

import java.util.List;

public class AxwayUserResponse {

    private List<AxwayUser> users;

    public List<AxwayUser> getUsers() {
        return users;
    }

    public void setUsers(List<AxwayUser> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "AxwayUserResponse{" +
                "users=" + users.toString() +
                '}';
    }
}
