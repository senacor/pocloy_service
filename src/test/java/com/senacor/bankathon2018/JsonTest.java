package com.senacor.bankathon2018;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senacor.bankathon2018.service.model.LoyaltyContent;
import com.senacor.bankathon2018.service.model.LoyaltyStatus;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.Credentials;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.ExchangeOfferDTO;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.ExchangeOfferToConsumeWithCredentials;
import com.senacor.bankathon2018.webendpoint.model.requestDTO.ExchangeOffersWithCredentials;
import com.senacor.bankathon2018.webendpoint.model.returnDTO.LoyaltyCodeDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
        LoyaltyContent.car_hatchback.toString(),
        LocalDateTime.now(),
        "2");

    List<LoyaltyCodeDTO> codes = new ArrayList<>();
    codes.add(loyaltyCodeDTO);
    codes.add(loyaltyCodeDTOUnpacked);

    ObjectMapper objectMapper = new ObjectMapper();
    System.out.println(objectMapper.writeValueAsString(codes));
  }

  @Test
  public void testExchangeOfferWithCredentials() throws JsonProcessingException {
    ExchangeOffersWithCredentials exchangeOffersWithCredentials = new ExchangeOffersWithCredentials();
    exchangeOffersWithCredentials.setCredentials(new Credentials("username", "password"));

    ExchangeOfferDTO firstOffer = new ExchangeOfferDTO();
    firstOffer.setExchangeOfferId(1);
    firstOffer.setOfferedStickerType(LoyaltyContent.cup);
    firstOffer.setOfferedStickerAmount(4);
    firstOffer.setRequiredStickerType(LoyaltyContent.gas_station);
    firstOffer.setRequiredStickerAmount(5);

    ExchangeOfferDTO secondOffer = new ExchangeOfferDTO();
    //secondOffer.setExchangeOfferId(47);
    secondOffer.setOfferedStickerType(LoyaltyContent.food);
    secondOffer.setOfferedStickerAmount(2);
    secondOffer.setRequiredStickerType(LoyaltyContent.sunglasses);
    secondOffer.setRequiredStickerAmount(3);

    List<ExchangeOfferDTO> offerList = Arrays.asList(firstOffer, secondOffer);

    ObjectMapper objectMapper = new ObjectMapper();
    System.out.println(objectMapper.writeValueAsString(offerList));

    exchangeOffersWithCredentials.setExchangeOfferDTOs(offerList);

    System.out.println(objectMapper.writeValueAsString(exchangeOffersWithCredentials));

    ExchangeOfferToConsumeWithCredentials exchangeOfferToConsumeWithCredentials = new ExchangeOfferToConsumeWithCredentials();
    exchangeOfferToConsumeWithCredentials.setCredentials(new Credentials("username", "password"));
    exchangeOfferToConsumeWithCredentials.setExchangeOfferToConsumeId(2);
    System.out.println(objectMapper.writeValueAsString(exchangeOfferToConsumeWithCredentials));

  }

}
