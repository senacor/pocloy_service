package com.senacor.bankathon2018.webendpoint.model.returnDTO;

import com.senacor.bankathon2018.service.model.BoughtVoucher;

public class BoughtVoucherDTO {

  private Long id;

  private Integer voucherTypeId;

  private String voucherTypeName;

  public BoughtVoucherDTO(BoughtVoucher boughtVoucher) {
    this.id = boughtVoucher.getId();
    this.voucherTypeId = boughtVoucher.getVoucherId();
    this.voucherTypeName = boughtVoucher.getName();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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
