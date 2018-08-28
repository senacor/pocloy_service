package com.senacor.bankathon2018.connectors.model.axway.user;

public class AxwayUserQueryResponse {

    private AxwayUserResponse response;

    public AxwayUserResponse getResponse() {
        return response;
    }

    public void setResponse(AxwayUserResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "AxwayUserQueryResponse{" +
                "response=" + response.toString() +
                '}';
    }
}
