package com.senacor.bankathon2018.service.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ExchangeOffer {

  @Id
  @GeneratedValue
  private Integer id;

  private LoyaltyContent offeredStickerType;

  private int offeredStickerAmount;

  private LoyaltyContent requiredStickerType;

  private int requiredSticker;

  public ExchangeOffer(LoyaltyContent offeredStickerType, int offeredStickerAmount,
      LoyaltyContent requiredStickerType, int requiredSticker) {
    this.offeredStickerType = offeredStickerType;
    this.offeredStickerAmount = offeredStickerAmount;
    this.requiredStickerType = requiredStickerType;
    this.requiredSticker = requiredSticker;
  }

  public ExchangeOffer() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public int getRequiredSticker() {
    return requiredSticker;
  }

  public void setRequiredSticker(int requiredSticker) {
    this.requiredSticker = requiredSticker;
  }
}
