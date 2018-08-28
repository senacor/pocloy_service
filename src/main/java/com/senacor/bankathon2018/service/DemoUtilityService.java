package com.senacor.bankathon2018.service;

import com.senacor.bankathon2018.service.repository.BoughtVoucherRepository;
import com.senacor.bankathon2018.service.repository.LoyaltyCodeRepository;
import org.springframework.stereotype.Service;

@Service
public class DemoUtilityService {

  private final LoyaltyCodeRepository loyaltyCodeRepository;
  private final BoughtVoucherRepository boughtVoucherRepository;

  public DemoUtilityService(
      LoyaltyCodeRepository loyaltyCodeRepository,
      BoughtVoucherRepository boughtVoucherRepository) {
    this.loyaltyCodeRepository = loyaltyCodeRepository;
    this.boughtVoucherRepository = boughtVoucherRepository;
  }

  public Void deleteDB() {
    boughtVoucherRepository.deleteAll();
    loyaltyCodeRepository.deleteAll();
    return null;
  }
}
