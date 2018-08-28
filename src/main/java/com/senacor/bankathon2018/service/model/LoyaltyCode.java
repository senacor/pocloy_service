package com.senacor.bankathon2018.service.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LoyaltyCode {

  private String loyaltyCode;

  private LoyaltyStatus status;

  private LoyaltyContent content;

  private Date paymentDate;

  @Id
  private String paymentTransactionId;

  private String user;

  public LoyaltyCode() {
  }

  public LoyaltyCode(String loyaltyCode, LoyaltyStatus status,
      LoyaltyContent content, Date paymentDate, String paymentTransactionId, String user) {
    this.loyaltyCode = loyaltyCode;
    this.status = status;
    this.content = content;
    this.paymentDate = paymentDate;
    this.paymentTransactionId = paymentTransactionId;
    this.user = user;
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

  public Date getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }

  public String getPaymentTransactionId() {
    return paymentTransactionId;
  }

  public void setPaymentTransactionId(String paymentTransactionId) {
    this.paymentTransactionId = paymentTransactionId;
  }
}
