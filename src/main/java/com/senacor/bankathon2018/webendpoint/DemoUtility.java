package com.senacor.bankathon2018.webendpoint;

import com.senacor.bankathon2018.service.DemoUtilityService;
import io.vavr.control.Try;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/demoutility")
public class DemoUtility {

  private final DemoUtilityService demoUtilityService;

  public DemoUtility(DemoUtilityService demoUtilityService) {
    this.demoUtilityService = demoUtilityService;
  }

  @GetMapping("/clearDB")
  public ResponseEntity<String> clearDB() {
    Try<Void> deletedDB = Try.of(() -> demoUtilityService.deleteDB());
    if (deletedDB.isSuccess()) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

}
