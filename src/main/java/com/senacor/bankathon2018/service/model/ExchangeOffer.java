package com.senacor.bankathon2018.service.model;

import com.senacor.bankathon2018.webendpoint.model.requestDTO.ExchangeOfferDTO;
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

  private int requiredStickerAmount;

  private String offeringUser;

  public ExchangeOffer(ExchangeOfferDTO exchangeOfferDTO, String offeringUser) {
    this.id = exchangeOfferDTO.getExchangeOfferId();
    this.offeredStickerType = exchangeOfferDTO.getOfferedStickerType();
    this.offeredStickerAmount = exchangeOfferDTO.getOfferedStickerAmount();
    this.requiredStickerType = exchangeOfferDTO.getRequiredStickerType();
    this.requiredStickerAmount = exchangeOfferDTO.getRequiredStickerAmount();
    this.offeringUser = offeringUser;
  }

  public ExchangeOffer(LoyaltyContent offeredStickerType, int offeredStickerAmount,
      LoyaltyContent requiredStickerType, int requiredStickerAmount, String offeringUser) {
    this.offeredStickerType = offeredStickerType;
    this.offeredStickerAmount = offeredStickerAmount;
    this.requiredStickerType = requiredStickerType;
    this.requiredStickerAmount = requiredStickerAmount;
    this.offeringUser = offeringUser;
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

  public int getRequiredStickerAmount() {
    return requiredStickerAmount;
  }

  public void setRequiredStickerAmount(int requiredStickerAmount) {
    this.requiredStickerAmount = requiredStickerAmount;
  }

  public String getOfferingUser() {
    return offeringUser;
  }

  public void setOfferingUser(String offeringUser) {
    this.offeringUser = offeringUser;
  }
}
