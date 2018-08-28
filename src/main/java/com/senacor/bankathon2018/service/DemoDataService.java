package com.senacor.bankathon2018.service;

import com.google.common.collect.ImmutableMap;
import com.senacor.bankathon2018.service.model.LoyaltyContent;
import com.senacor.bankathon2018.service.model.Voucher;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DemoDataService {

  private List<Voucher> vouchers;


  public DemoDataService() {
    vouchers = new ArrayList<>();
  }

  @EventListener
  public void appReady(ApplicationReadyEvent event) {
    //define existing vouchers
    vouchers.add(new Voucher(1, "Burger",
        ImmutableMap.of(LoyaltyContent.bottle_wine, 2, LoyaltyContent.food_apple, 3)));
    vouchers.add(new Voucher(2, "Crockery set",
        ImmutableMap.of(LoyaltyContent.cup, 10, LoyaltyContent.silverware_fork_knife, 10)));
    vouchers.add(new Voucher(3, "Sunglasses",
        ImmutableMap.of(LoyaltyContent.sunglasses, 5, LoyaltyContent.white_balance_sunny, 10)));
    vouchers.add(new Voucher(4, "10L of Petrol",
        ImmutableMap.of(LoyaltyContent.gas_station, 5, LoyaltyContent.car_hatchback, 5)));
    vouchers.add(new Voucher(5, "Hamburger", ImmutableMap.of(LoyaltyContent.hamburger, 1)));
    vouchers.add(new Voucher(6, "Wein", ImmutableMap.of(LoyaltyContent.bottle_wine, 1)));
    vouchers.add(new Voucher(7, "Auto", ImmutableMap.of(LoyaltyContent.car_hatchback, 1)));
    vouchers.add(new Voucher(8, "Cup", ImmutableMap.of(LoyaltyContent.cup, 1)));
    vouchers.add(new Voucher(9, "Apple", ImmutableMap.of(LoyaltyContent.food_apple, 1)));
    vouchers.add(new Voucher(10, "Food", ImmutableMap.of(LoyaltyContent.food, 1)));
    vouchers.add(new Voucher(11, "Gas", ImmutableMap.of(LoyaltyContent.gas_station, 1)));
    vouchers.add(
        new Voucher(12, "Silverware", ImmutableMap.of(LoyaltyContent.silverware_fork_knife, 1)));
    vouchers.add(new Voucher(13, "Sunglasses", ImmutableMap.of(LoyaltyContent.sunglasses, 1)));
    vouchers.add(new Voucher(14, "Sun", ImmutableMap.of(LoyaltyContent.white_balance_sunny, 1)));
  }

  public List<Voucher> getVouchers() {
    return vouchers;
  }

  public Voucher getVoucherById(Integer id) {
    for (Voucher voucher : vouchers) {
      if (voucher.getId().equals(id)) {
        return voucher;
      }
    }
    return null;
  }
}
