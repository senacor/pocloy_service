package com.senacor.bankathon2018;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senacor.bankathon2018.service.model.LoyaltyContent;
import com.senacor.bankathon2018.service.model.LoyaltyStatus;
import com.senacor.bankathon2018.webendpoint.model.dto.LoyaltyCodeDTO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;

public class JsonTest {

  @Test
  public void testLoyaltyCodeToJson() throws JsonProcessingException {
    LoyaltyCodeDTO loyaltyCodeDTO = new LoyaltyCodeDTO(
        "ae546c90-c381-4e73-b38d-0565f1200b94",
        LoyaltyStatus.packed.toString(),
        LoyaltyContent.unknown.toString(),
        Calendar.getInstance().getTime(),
        "3");

    ObjectMapper objectMapper = new ObjectMapper();
    System.out.println(objectMapper.writeValueAsString(loyaltyCodeDTO));
  }

  @Test
  public void testLoyaltyCodeListToJson() throws JsonProcessingException {
    LoyaltyCodeDTO loyaltyCodeDTO = new LoyaltyCodeDTO(
        "ae546c90-c381-4e73-b38d-0565f1200b94",
        LoyaltyStatus.packed.toString(),
        LoyaltyContent.unknown.toString(),
        Calendar.getInstance().getTime(),
        "1");

    LoyaltyCodeDTO loyaltyCodeDTOUnpacked = new LoyaltyCodeDTO(
        "96e86953-c71a-40f0-af60-5bd83d7a77c0",
        LoyaltyStatus.unpacked.toString(),
        LoyaltyContent.sun.toString(),
        Calendar.getInstance().getTime(),
        "2");

    List<LoyaltyCodeDTO> codes = new ArrayList<>();
    codes.add(loyaltyCodeDTO);
    codes.add(loyaltyCodeDTOUnpacked);

    ObjectMapper objectMapper = new ObjectMapper();
    System.out.println(objectMapper.writeValueAsString(codes));
  }

}
