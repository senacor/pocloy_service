package com.senacor.bankathon2018.webendpoint.model.requestDTO;

import java.util.List;

public class ExchangeOffersWithCredentials {

  private List<ExchangeOfferDTO> exchangeOfferDTOs;

  private Credentials credentials;

  public List<ExchangeOfferDTO> getExchangeOfferDTOs() {
    return exchangeOfferDTOs;
  }

  public void setExchangeOfferDTOs(
      List<ExchangeOfferDTO> exchangeOfferDTOs) {
    this.exchangeOfferDTOs = exchangeOfferDTOs;
  }

  public Credentials getCredentials() {
    return credentials;
  }

  public void setCredentials(
      Credentials credentials) {
    this.credentials = credentials;
  }
}
