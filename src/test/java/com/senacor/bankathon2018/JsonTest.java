package com.senacor.bankathon2018;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senacor.bankathon2018.dto.LoyaltyCode;
import com.senacor.bankathon2018.dto.LoyaltyContent;
import com.senacor.bankathon2018.dto.LoyaltyStatus;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;

public class JsonTest {

  @Test
  public void testLoyaltyCodeToJson() throws JsonProcessingException {
    LoyaltyCode loyaltyCode = new LoyaltyCode(
        "ae546c90-c381-4e73-b38d-0565f1200b94",
        LoyaltyStatus.packed.toString(),
        LoyaltyContent.unknown.toString(),
        Calendar.getInstance().getTime());

    ObjectMapper objectMapper = new ObjectMapper();
    System.out.println(objectMapper.writeValueAsString(loyaltyCode));
  }

  @Test
  public void testLoyaltyCodeListToJson() throws JsonProcessingException {
    LoyaltyCode loyaltyCode = new LoyaltyCode(
        "ae546c90-c381-4e73-b38d-0565f1200b94",
        LoyaltyStatus.packed.toString(),
        LoyaltyContent.unknown.toString(),
        Calendar.getInstance().getTime());

    LoyaltyCode loyaltyCodeUnpacked = new LoyaltyCode(
        "96e86953-c71a-40f0-af60-5bd83d7a77c0",
        LoyaltyStatus.unpacked.toString(),
        LoyaltyContent.sun.toString(),
        Calendar.getInstance().getTime());

    List<LoyaltyCode> codes = new ArrayList<>();
    codes.add(loyaltyCode);
    codes.add(loyaltyCodeUnpacked);

    ObjectMapper objectMapper = new ObjectMapper();
    System.out.println(objectMapper.writeValueAsString(codes));
  }

}
