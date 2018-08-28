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
    vouchers.add(new Voucher(1L, "Burger",
        ImmutableMap.of(LoyaltyContent.bottle_wine, 2, LoyaltyContent.food_apple, 3)));
    vouchers.add(new Voucher(2L, "Crockery set",
        ImmutableMap.of(LoyaltyContent.cup, 10, LoyaltyContent.silverware_fork_knife, 10)));
    vouchers.add(new Voucher(3L, "Sunglasses",
        ImmutableMap.of(LoyaltyContent.sunglasses, 5, LoyaltyContent.white_balance_sunny, 10)));
    vouchers.add(new Voucher(3L, "10L of Petrol",
        ImmutableMap.of(LoyaltyContent.gas_station, 5, LoyaltyContent.car_hatchback, 5)));
  }

  public List<Voucher> getVouchers() {
    return vouchers;
  }
}
