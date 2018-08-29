package com.senacor.bankathon2018.webendpoint.model.requestDTO;

public class ExchangeOfferToConsumeWithCredentials {

  private Integer exchangeOfferToConsumeId;

  private Credentials credentials;

  public Integer getExchangeOfferToConsumeId() {
    return exchangeOfferToConsumeId;
  }

  public void setExchangeOfferToConsumeId(Integer exchangeOfferToConsumeId) {
    this.exchangeOfferToConsumeId = exchangeOfferToConsumeId;
  }

  public Credentials getCredentials() {
    return credentials;
  }

  public void setCredentials(
      Credentials credentials) {
    this.credentials = credentials;
  }
}
