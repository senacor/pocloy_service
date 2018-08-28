package com.senacor.bankathon2018.webendpoint.model;

public enum LoyaltyStatus {

  packed,
  unpacked;

  @Override
  public String toString() {
    return this.name();
  }
}
