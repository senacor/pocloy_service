package com.senacor.bankathon2018.service.model;

public enum LoyaltyContent {

  unknown,
  bottle_wine,
  car_hatchback,
  cup,
  food_apple,
  food,
  gas_station,
  hamburger,
  pizza,
  silverware_fork_knife,
  sunglasses,
  white_balance_sunny;

  @Override
  public String toString() {
    return this.name();
  }

}
