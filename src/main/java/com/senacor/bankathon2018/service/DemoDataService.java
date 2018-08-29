package com.senacor.bankathon2018.service;

import com.google.common.collect.ImmutableMap;
import com.senacor.bankathon2018.service.model.BoughtVoucher;
import com.senacor.bankathon2018.service.model.ExchangeOffer;
import com.senacor.bankathon2018.service.model.LoyaltyCode;
import com.senacor.bankathon2018.service.model.LoyaltyContent;
import com.senacor.bankathon2018.service.model.LoyaltyStatus;
import com.senacor.bankathon2018.service.model.Voucher;
import com.senacor.bankathon2018.service.repository.BoughtVoucherRepository;
import com.senacor.bankathon2018.service.repository.ExchangeOfferRepository;
import com.senacor.bankathon2018.service.repository.LoyaltyCodeRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DemoDataService {

  private List<Voucher> vouchers;
  private final BoughtVoucherRepository boughtVoucherRepository;
  private final LoyaltyCodeRepository loyaltyCodeRepository;
  private final ExchangeOfferRepository exchangeOfferRepository;
  private static final String testUserName = "christoph@prybila.at";


  public DemoDataService(
      BoughtVoucherRepository boughtVoucherRepository,
      LoyaltyCodeRepository loyaltyCodeRepository,
      ExchangeOfferRepository exchangeOfferRepository) {
    this.boughtVoucherRepository = boughtVoucherRepository;
    this.loyaltyCodeRepository = loyaltyCodeRepository;
    this.exchangeOfferRepository = exchangeOfferRepository;
    vouchers = new ArrayList<>();
  }

  @EventListener
  public void appReady(ApplicationReadyEvent event) {
    insertAvailableVouchers();
    insertAlreadyBoughtVouchers();
    insertLoyaltyCodesForTestUser();
    insertExchangeOffersForTestUser();
  }

  private void insertLoyaltyCodesForTestUser() {
    for (int i = 0; i < LoyaltyContent.values().length; i++) {
      LoyaltyContent currentContent = LoyaltyContent.values()[i];
      loyaltyCodeRepository.save(
          new LoyaltyCode(UUID.randomUUID().toString(), LoyaltyStatus.unpacked, currentContent,
              LocalDateTime.now(), UUID.randomUUID().toString(), testUserName));
      loyaltyCodeRepository.save(
          new LoyaltyCode(UUID.randomUUID().toString(), LoyaltyStatus.unpacked, currentContent,
              LocalDateTime.now(), UUID.randomUUID().toString(), testUserName));
    }
  }

  private void insertExchangeOffersForTestUser() {
    exchangeOfferRepository.save(
        new ExchangeOffer(LoyaltyContent.car_hatchback, 2, LoyaltyContent.cup, 1, testUserName));
    exchangeOfferRepository.save(
        new ExchangeOffer(LoyaltyContent.bottle_wine, 1, LoyaltyContent.silverware_fork_knife, 1,
            testUserName));
    exchangeOfferRepository.save(
        new ExchangeOffer(LoyaltyContent.hamburger, 2, LoyaltyContent.gas_station, 1,
            testUserName));
    exchangeOfferRepository.save(
        new ExchangeOffer(LoyaltyContent.white_balance_sunny, 1, LoyaltyContent.pizza, 1,
            testUserName));
    exchangeOfferRepository.save(
        new ExchangeOffer(LoyaltyContent.food, 1, LoyaltyContent.sunglasses, 1, testUserName));
    exchangeOfferRepository.save(
        new ExchangeOffer(LoyaltyContent.food, 1, LoyaltyContent.bottle_wine, 1, testUserName));
    exchangeOfferRepository.save(
        new ExchangeOffer(LoyaltyContent.food, 1, LoyaltyContent.car_hatchback, 1, testUserName));
    exchangeOfferRepository.save(
        new ExchangeOffer(LoyaltyContent.food, 1, LoyaltyContent.food_apple, 1, testUserName));
    exchangeOfferRepository
        .save(new ExchangeOffer(LoyaltyContent.food, 1, LoyaltyContent.food, 1, testUserName));
    exchangeOfferRepository
        .save(new ExchangeOffer(LoyaltyContent.food, 1, LoyaltyContent.hamburger, 1, testUserName));
    exchangeOfferRepository.save(
        new ExchangeOffer(LoyaltyContent.food, 1, LoyaltyContent.white_balance_sunny, 1,
            testUserName));
  }

  private void insertAlreadyBoughtVouchers() {
    boughtVoucherRepository.save(new BoughtVoucher(1, "Burger", "blalasaadri@googlemail.com"));
    boughtVoucherRepository
        .save(new BoughtVoucher(4, "10L of Petrol", "blalasaadri@googlemail.com"));
    boughtVoucherRepository
        .save(new BoughtVoucher(2, "Crockery set", "blalasaadri@googlemail.com"));
    boughtVoucherRepository.save(new BoughtVoucher(3, "Sunglasses", "blalasaadri@googlemail.com"));
  }

  private void insertAvailableVouchers() {
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

  public Voucher getVoucherTypeById(Integer id) {
    for (Voucher voucher : vouchers) {
      if (voucher.getId().equals(id)) {
        return voucher;
      }
    }
    return null;
  }
}
