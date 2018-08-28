package com.senacor.bankathon2018.service.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BoughtVoucher {

  @Id
  private Long id;

  private Long voucherId;

  private String name;

  private String user;

  public BoughtVoucher(Long id, Long voucherId, String name, String user) {
    this.id = id;
    this.voucherId = voucherId;
    this.name = name;
    this.user = user;
  }

  public BoughtVoucher() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getVoucherId() {
    return voucherId;
  }

  public void setVoucherId(Long voucherId) {
    this.voucherId = voucherId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }
}
