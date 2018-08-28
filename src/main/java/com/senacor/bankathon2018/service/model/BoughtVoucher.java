package com.senacor.bankathon2018.service.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BoughtVoucher {

  @Id
  @GeneratedValue
  private Integer id;

  private Integer voucherTypeId;

  private String voucherTypeName;

  private String user;

  private boolean consumed;

  public BoughtVoucher(Integer voucherTypeId, String voucherTypeName, String user) {
    this.voucherTypeId = voucherTypeId;
    this.voucherTypeName = voucherTypeName;
    this.user = user;
    this.consumed = false;
  }

  public BoughtVoucher() {
  }

  public boolean isConsumed() {
    return consumed;
  }

  public void setConsumed(boolean consumed) {
    this.consumed = consumed;
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

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }
}
