package com.senacor.bankathon2018.webendpoint.model.requestDTO;

public class LoyaltyCodeWithCredentials {

  private Credentials credentials;

  private String codeId;

  public Credentials getCredentials() {
    return credentials;
  }

  public void setCredentials(Credentials credentials) {
    this.credentials = credentials;
  }

  public String getCodeId() {
    return codeId;
  }

  public void setCodeId(String codeId) {
    this.codeId = codeId;
  }
}
