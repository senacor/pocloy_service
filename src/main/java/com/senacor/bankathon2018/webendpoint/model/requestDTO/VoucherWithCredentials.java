package com.senacor.bankathon2018.webendpoint.model.requestDTO;

public class VoucherWithCredentials {

  private Credentials credentials;

  private Integer voucherId;

  public Credentials getCredentials() {
    return credentials;
  }

  public void setCredentials(Credentials credentials) {
    this.credentials = credentials;
  }

  public Integer getVoucherId() {
    return voucherId;
  }

  public void setVoucherId(Integer voucherId) {
    this.voucherId = voucherId;
  }
}
