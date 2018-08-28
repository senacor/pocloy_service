package com.senacor.bankathon2018.service.model;

public enum LoyaltyContent {

  unknown,
  sun,
  pizza,
  cake;

  @Override
  public String toString() {
    return this.name();
  }

}
