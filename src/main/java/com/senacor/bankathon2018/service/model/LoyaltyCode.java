package com.senacor.bankathon2018.service.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LoyaltyCode {

  private String loyaltyCode;

  private LoyaltyStatus status;

  private LoyaltyContent content;

  private LocalDateTime paymentDate;

  @Id
  private String paymentTransactionId;

  private String user;

  private boolean deleted;

  public LoyaltyCode() {
  }

  public LoyaltyCode(String loyaltyCode, LoyaltyStatus status,
      LoyaltyContent content, LocalDateTime paymentDate, String paymentTransactionId, String user) {
    this.loyaltyCode = loyaltyCode;
    this.status = status;
    this.content = content;
    this.paymentDate = paymentDate;
    this.paymentTransactionId = paymentTransactionId;
    this.user = user;
    this.deleted = false;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getLoyaltyCode() {
    return loyaltyCode;
  }

  public void setLoyaltyCode(String loyaltyCode) {
    this.loyaltyCode = loyaltyCode;
  }

  public LoyaltyStatus getStatus() {
    return status;
  }

  public void setStatus(LoyaltyStatus status) {
    this.status = status;
  }

  public LoyaltyContent getContent() {
    return content;
  }

  public void setContent(LoyaltyContent content) {
    this.content = content;
  }

  public LocalDateTime getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(LocalDateTime paymentDate) {
    this.paymentDate = paymentDate;
  }

  public String getPaymentTransactionId() {
    return paymentTransactionId;
  }

  public void setPaymentTransactionId(String paymentTransactionId) {
    this.paymentTransactionId = paymentTransactionId;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }
}
