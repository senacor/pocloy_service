package com.senacor.bankathon2018.webendpoint.model.requestDTO;

import com.senacor.bankathon2018.service.model.ExchangeOffer;
import com.senacor.bankathon2018.service.model.LoyaltyContent;

public class ExchangeOfferDTO {

  private Integer exchangeOfferId;

  private LoyaltyContent offeredStickerType;

  private int offeredStickerAmount;

  private LoyaltyContent requiredStickerType;

  private int requiredStickerAmount;

  public ExchangeOfferDTO() {
  }

  public ExchangeOfferDTO(ExchangeOffer exchangeOffer) {
    this.exchangeOfferId = exchangeOffer.getId();
    this.offeredStickerType = exchangeOffer.getOfferedStickerType();
    this.offeredStickerAmount = exchangeOffer.getOfferedStickerAmount();
    this.requiredStickerType = exchangeOffer.getRequiredStickerType();
    this.requiredStickerAmount = exchangeOffer.getRequiredStickerAmount();
  }

  public Integer getExchangeOfferId() {
    return exchangeOfferId;
  }

  public void setExchangeOfferId(Integer exchangeOfferId) {
    this.exchangeOfferId = exchangeOfferId;
  }

  public LoyaltyContent getOfferedStickerType() {
    return offeredStickerType;
  }

  public void setOfferedStickerType(
      LoyaltyContent offeredStickerType) {
    this.offeredStickerType = offeredStickerType;
  }

  public int getOfferedStickerAmount() {
    return offeredStickerAmount;
  }

  public void setOfferedStickerAmount(int offeredStickerAmount) {
    this.offeredStickerAmount = offeredStickerAmount;
  }

  public LoyaltyContent getRequiredStickerType() {
    return requiredStickerType;
  }

  public void setRequiredStickerType(
      LoyaltyContent requiredStickerType) {
    this.requiredStickerType = requiredStickerType;
  }

  public int getRequiredStickerAmount() {
    return requiredStickerAmount;
  }

  public void setRequiredStickerAmount(int requiredStickerAmount) {
    this.requiredStickerAmount = requiredStickerAmount;
  }
}
