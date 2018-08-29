package com.senacor.bankathon2018.service;

import com.senacor.bankathon2018.service.repository.BoughtVoucherRepository;
import com.senacor.bankathon2018.service.repository.ExchangeOfferRepository;
import com.senacor.bankathon2018.service.repository.LoyaltyCodeRepository;
import org.springframework.stereotype.Service;

@Service
public class DemoUtilityService {

  private final LoyaltyCodeRepository loyaltyCodeRepository;
  private final BoughtVoucherRepository boughtVoucherRepository;
  private final ExchangeOfferRepository exchangeOfferRepository;
  private final DemoDataService demoDataService;

  public DemoUtilityService(
      LoyaltyCodeRepository loyaltyCodeRepository,
      BoughtVoucherRepository boughtVoucherRepository,
      ExchangeOfferRepository exchangeOfferRepository,
      DemoDataService demoDataService) {
    this.loyaltyCodeRepository = loyaltyCodeRepository;
    this.boughtVoucherRepository = boughtVoucherRepository;

    this.exchangeOfferRepository = exchangeOfferRepository;
    this.demoDataService = demoDataService;
  }

  public Void deleteDB() {
    boughtVoucherRepository.deleteAll();
    loyaltyCodeRepository.deleteAll();
    exchangeOfferRepository.deleteAll();
    demoDataService.appReady(null);
    return null;
  }
}
