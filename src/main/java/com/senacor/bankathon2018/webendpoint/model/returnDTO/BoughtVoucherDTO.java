package com.senacor.bankathon2018.webendpoint.model.returnDTO;

import com.senacor.bankathon2018.service.model.BoughtVoucher;

public class BoughtVoucherDTO {

  private Long id;

  private Integer voucherId;

  private String voucherName;

  public BoughtVoucherDTO(BoughtVoucher boughtVoucher) {
    this.id = boughtVoucher.getId();
    this.voucherId = boughtVoucher.getVoucherId();
    this.voucherName = boughtVoucher.getName();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getVoucherId() {
    return voucherId;
  }

  public void setVoucherId(Integer voucherId) {
    this.voucherId = voucherId;
  }

  public String getVoucherName() {
    return voucherName;
  }

  public void setVoucherName(String voucherName) {
    this.voucherName = voucherName;
  }
}
