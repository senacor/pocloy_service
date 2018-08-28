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
    vouchers.add(new Voucher(5, "Burger",
        ImmutableMap.of(LoyaltyContent.hamburger, 1)));
    vouchers.add(new Voucher(6, "10L of Petrol",
        ImmutableMap.of(LoyaltyContent.pizza, 1)));
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
