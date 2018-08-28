package com.senacor.bankathon2018.webendpoint.model.requestDTO;

public class VoucherTypeWithCredentials {

  private Credentials credentials;

  private Integer voucherTypeId;

  public Credentials getCredentials() {
    return credentials;
  }

  public void setCredentials(Credentials credentials) {
    this.credentials = credentials;
  }

  public Integer getVoucherTypeId() {
    return voucherTypeId;
  }

  public void setVoucherTypeId(Integer voucherTypeId) {
    this.voucherTypeId = voucherTypeId;
  }
}
