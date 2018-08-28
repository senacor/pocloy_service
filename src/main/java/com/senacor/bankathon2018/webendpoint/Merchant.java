package com.senacor.bankathon2018.webendpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senacor.bankathon2018.service.VoucherService;
import com.senacor.bankathon2018.service.model.Voucher;
import io.vavr.control.Try;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/merchant")
public class Merchant {

  private final VoucherService voucherService;

  public Merchant(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @GetMapping("/vouchers")
  public ResponseEntity<String> vouchers()
      throws JsonProcessingException {
    Try<List<Voucher>> vouchers = Try.of(() -> voucherService.getVouchers());
    ObjectMapper objectMapper = new ObjectMapper();
    String vouchersAsJson;

    if (vouchers.isSuccess()) {
      vouchersAsJson = objectMapper.writeValueAsString(vouchers.get());
      return ResponseEntity.ok(vouchersAsJson);
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

}
