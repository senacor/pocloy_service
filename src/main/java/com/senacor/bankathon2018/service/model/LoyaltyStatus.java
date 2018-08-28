package com.senacor.bankathon2018.service.model;

public enum LoyaltyStatus {

  packed,
  unpacked;

  @Override
  public String toString() {
    return this.name();
  }
}
