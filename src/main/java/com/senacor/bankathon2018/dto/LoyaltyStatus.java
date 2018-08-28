package com.senacor.bankathon2018.dto;

public enum LoyaltyStatus {

  packed,
  unpacked;

  @Override
  public String toString() {
    return this.name();
  }
}
