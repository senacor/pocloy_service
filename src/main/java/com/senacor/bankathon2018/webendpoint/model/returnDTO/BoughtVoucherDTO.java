package com.senacor.bankathon2018.webendpoint.model.returnDTO;

import com.senacor.bankathon2018.service.model.BoughtVoucher;

public class BoughtVoucherDTO {

  private Integer id;

  private Integer voucherTypeId;

  private String voucherTypeName;

  public BoughtVoucherDTO(BoughtVoucher boughtVoucher) {
    this.id = boughtVoucher.getId();
    this.voucherTypeId = boughtVoucher.getVoucherTypeId();
    this.voucherTypeName = boughtVoucher.getVoucherTypeName();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getVoucherTypeId() {
    return voucherTypeId;
  }

  public void setVoucherTypeId(Integer voucherTypeId) {
    this.voucherTypeId = voucherTypeId;
  }

  public String getVoucherTypeName() {
    return voucherTypeName;
  }

  public void setVoucherTypeName(String voucherTypeName) {
    this.voucherTypeName = voucherTypeName;
  }
}
