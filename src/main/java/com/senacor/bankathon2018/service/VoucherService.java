package com.senacor.bankathon2018.service;

import com.senacor.bankathon2018.service.model.Voucher;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

  private final DemoDataService demoDataService;

  public VoucherService(DemoDataService demoDataService) {
    this.demoDataService = demoDataService;
  }

  public List<Voucher> getVouchers() {
    return demoDataService.getVouchers();
  }

}
