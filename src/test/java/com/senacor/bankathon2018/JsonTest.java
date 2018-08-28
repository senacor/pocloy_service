package com.senacor.bankathon2018;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senacor.bankathon2018.service.model.LoyaltyContent;
import com.senacor.bankathon2018.service.model.LoyaltyStatus;
import com.senacor.bankathon2018.webendpoint.model.dto.LoyaltyCodeDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class JsonTest {

  @Test
  public void testLoyaltyCodeToJson() throws JsonProcessingException {
    LoyaltyCodeDTO loyaltyCodeDTO = new LoyaltyCodeDTO(
        LoyaltyStatus.packed.toString(),
        LoyaltyContent.unknown.toString(),
        LocalDateTime.now(),
        "3");

    ObjectMapper objectMapper = new ObjectMapper();
    System.out.println(objectMapper.writeValueAsString(loyaltyCodeDTO));
  }

  @Test
  public void testLoyaltyCodeListToJson() throws JsonProcessingException {
    LoyaltyCodeDTO loyaltyCodeDTO = new LoyaltyCodeDTO(
        LoyaltyStatus.packed.toString(),
        LoyaltyContent.unknown.toString(),
        LocalDateTime.now(),
        "1");

    LoyaltyCodeDTO loyaltyCodeDTOUnpacked = new LoyaltyCodeDTO(
        LoyaltyStatus.unpacked.toString(),
        LoyaltyContent.sun.toString(),
        LocalDateTime.now(),
        "2");

    List<LoyaltyCodeDTO> codes = new ArrayList<>();
    codes.add(loyaltyCodeDTO);
    codes.add(loyaltyCodeDTOUnpacked);

    ObjectMapper objectMapper = new ObjectMapper();
    System.out.println(objectMapper.writeValueAsString(codes));
  }

}
