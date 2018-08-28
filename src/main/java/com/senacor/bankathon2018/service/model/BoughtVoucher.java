package com.senacor.bankathon2018.service.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BoughtVoucher {

  @Id
  @GeneratedValue
  private Long id;

  private Integer voucherId;

  private String name;

  private String user;

  public BoughtVoucher(Integer voucherId, String name, String user) {
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

  public Integer getVoucherId() {
    return voucherId;
  }

  public void setVoucherId(Integer voucherId) {
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
