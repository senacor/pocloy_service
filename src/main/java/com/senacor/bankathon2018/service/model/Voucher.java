package com.senacor.bankathon2018.service.model;

import java.util.Map;

public class Voucher {

  private Integer id;

  private String name;

  private Map<LoyaltyContent, Integer> price;

  public Voucher() {
  }

  public Voucher(Integer id, String name, Map<LoyaltyContent, Integer> price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<LoyaltyContent, Integer> getPrice() {
    return price;
  }

  public void setPrice(Map<LoyaltyContent, Integer> price) {
    this.price = price;
  }
}
